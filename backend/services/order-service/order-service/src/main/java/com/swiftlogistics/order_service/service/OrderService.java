package com.swiftlogistics.order_service.service;

import com.swiftlogistics.order_service.enums.OrderStatus;
import com.swiftlogistics.order_service.enums.PaymentStatus;
import com.swiftlogistics.order_service.enums.DeliveryStatus;
import com.swiftlogistics.order_service.model.*;
import com.swiftlogistics.order_service.repository.CartRepository;
import com.swiftlogistics.order_service.repository.CartItemRepository;
import com.swiftlogistics.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.swiftlogistics.order_service.dto.response.ProductResponseDTO;
import com.swiftlogistics.order_service.events.OrderEventPublisher;
import com.swiftlogistics.order_service.events.OrderCreatedEvent;
import java.time.LocalDateTime;
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


    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

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

    public Order placeOrder(List<CartItem> cartItems,  int userId) {
        Order order = new Order();
        order.setUserId(userId);
        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0.0;

        String productServiceUrl = "http://localhost:8080/api/product/id/";

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());

            try {
                ProductResponseDTO product = restTemplate.getForObject(
                        productServiceUrl + cartItem.getProductId(),
                        ProductResponseDTO.class
                );
                System.out.println("Fetched product: " + product);

                if (product != null) {
                    double unitPrice = product.getProductPrice();
                    orderItem.setUnitPrice(unitPrice);
                    total += unitPrice * orderItem.getQuantity();
                } else {
                    orderItem.setUnitPrice(0.0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                orderItem.setUnitPrice(0.0);
            }

            orderItem.setOrder(order);
            orderItem.setOrderStatus(OrderStatus.PENDING);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotal(total);
        order.setOrderStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);

        // Publish OrderCreatedEvent
        OrderCreatedEvent event = new OrderCreatedEvent(
                (long) savedOrder.getOrderId(),
                "User-" + savedOrder.getUserId(),
                "CREATED"
        );
        orderEventPublisher.publishOrderCreated(event);

        return savedOrder;

    }
}
