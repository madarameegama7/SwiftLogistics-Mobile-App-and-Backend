package com.swiftlogistics.order_service.controller;

import com.swiftlogistics.order_service.dto.request.UpdateStatusRequest;
import com.swiftlogistics.order_service.model.Cart;
import com.swiftlogistics.order_service.model.Order;
import com.swiftlogistics.order_service.model.CartItem;
import com.swiftlogistics.order_service.service.CartService;
import com.swiftlogistics.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import com.swiftlogistics.order_service.security.JwtUtil;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final OrderService orderService;
    private final JwtUtil jwtUtil;

    @PostMapping("/addtoCart")
    public ResponseEntity<Cart> addItemToCart(@RequestBody Cart cart, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String userId = jwtUtil.extractUserId(token);
            cart.setUserId(Integer.parseInt(userId));

            if (cart.getItems() != null) {
                for (CartItem item : cart.getItems()) {
                    item.setCart(cart);
                }
            }

            try {
                Cart updatedCart = cartService.addItemToCart(cart);
                return ResponseEntity.ok(updatedCart);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getCartByCartId/{cartId}")
    public ResponseEntity<Cart> getCartByCartId(@PathVariable int cartId) {
        return cartService.getCartByCartId(cartId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getCartByUserId/{userId}")
    public ResponseEntity<List<Cart>> getCartByUserId(@PathVariable int userId) {
        List<Cart> carts = cartService.getCartByUserId(userId);
        if (carts.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(carts); // 200 OK with list
    }

    @PostMapping("/checkout/{cartId}")
    public ResponseEntity<Order> checkoutCart(@PathVariable int cartId) {
        try {
            Order order = orderService.checkoutCart(cartId);
            if (order != null) {
                return new ResponseEntity<>(order, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

  @PutMapping("/updateStatus/{orderId}")
public ResponseEntity<Order> updateOrderStatus(
        @PathVariable int orderId,
        @RequestBody UpdateStatusRequest request) {
    try {
        Order updatedOrder = orderService.updateOrderStatus(orderId, request.getStatus());
        return ResponseEntity.ok(updatedOrder);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}


}