package com.swiftlogistics.order_service.service;

import com.swiftlogistics.order_service.enums.OrderStatus;
import com.swiftlogistics.order_service.model.*;
import com.swiftlogistics.order_service.repository.CartRepository;
import com.swiftlogistics.order_service.repository.CartItemRepository;
import com.swiftlogistics.order_service.repository.OrderRepository;
import com.swiftlogistics.order_service.repository.OrderStatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.swiftlogistics.order_service.dto.response.ProductResponseDTO;
import com.swiftlogistics.order_service.events.OrderEventPublisher;
import com.swiftlogistics.order_service.events.OrderCreatedEvent;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private OrderEventPublisher orderEventPublisher;
    @Autowired
    private OrderStatusHistoryRepository orderStatusHistoryRepository;


    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    // === Checkout cart and place order ===
    public Order checkoutCart(int cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isEmpty()) {
            throw new RuntimeException("Cart not found with ID: " + cartId);
        }

        Cart cart = optionalCart.get();
        List<CartItem> cartItems = cartItemRepository.findByCart_CartId(cartId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty.");
        }

        return placeOrder(cartItems, cart.getUserId());
    }

    // === Place order ===
    public Order placeOrder(List<CartItem> cartItems, int userId) {
        Order order = new Order();
        order.setUserId(userId);
        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0.0;

        String productServiceUrl = "http://localhost:8082/api/product/id/";

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());

            try {
                ProductResponseDTO product = restTemplate.getForObject(
                        productServiceUrl + cartItem.getProductId(),
                        ProductResponseDTO.class
                );

                if (product != null && product.getProductPrice() > 0) {
                    double unitPrice = product.getProductPrice();
                    orderItem.setUnitPrice(unitPrice);
                    total += unitPrice * orderItem.getQuantity();
                } else {
                    System.out.println("⚠️ Product not found or price missing for productId=" + cartItem.getProductId());
                    orderItem.setUnitPrice(0.0); // fallback
                }
            } catch (Exception e) {
                System.out.println("⚠️ Failed to fetch product for productId=" + cartItem.getProductId());
                e.printStackTrace();
                orderItem.setUnitPrice(0.0); // fallback
            }

            orderItem.setOrder(order);
            orderItem.setOrderStatus(OrderStatus.CREATED);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotal(total);
        order.setOrderStatus(OrderStatus.CREATED);

        Order savedOrder = orderRepository.save(order);

        // Publish event
        OrderCreatedEvent event = new OrderCreatedEvent(
                (long) savedOrder.getOrderId(),
                "User-" + savedOrder.getUserId(),
                savedOrder.getOrderStatus().name()
        );
        orderEventPublisher.publishOrderCreated(event);

        System.out.println("✅ Order placed: " + savedOrder);
        return savedOrder;
    }


    // === Update order status (called by orchestrator) ===
    public Order updateOrderStatus(int orderId, String status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }

        Order order = optionalOrder.get();

        try {
            OrderStatus newStatus = OrderStatus.valueOf(status);
            order.setOrderStatus(newStatus);
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ Unknown enum value, storing as raw string");
            order.setOrderStatus(OrderStatus.FAILED); // fallback
        }

        Order savedOrder = orderRepository.save(order);

        // --- Record status history ---
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrderId(orderId);
        history.setStatus(status);
        orderStatusHistoryRepository.save(history);
        System.out.println("📚 Recorded status in history: " + status);

        return savedOrder;
    }

    public List<Map<String, Object>> getOrdersWithStatusHistory(int userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Order order : orders) {
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("order", order);

            List<OrderStatusHistory> historyList = orderStatusHistoryRepository.findByOrderId(order.getOrderId());
            orderMap.put("statusHistory", historyList);

            result.add(orderMap);
        }

        return result;
    }



}
