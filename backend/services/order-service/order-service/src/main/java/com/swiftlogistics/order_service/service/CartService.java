package com.swiftlogistics.order_service.service;

import com.swiftlogistics.order_service.model.Cart;
import com.swiftlogistics.order_service.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.swiftlogistics.order_service.model.CartItem;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    // Add item to cart
    public Cart addItemToCart(Cart newCart) {
        List<Cart> existingCarts = cartRepository.findByUserId(newCart.getUserId());

        Cart cart = existingCarts.isEmpty() ? new Cart() : existingCarts.get(0);
        cart.setUserId(newCart.getUserId());

        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }

        for (CartItem newItem : newCart.getItems()) {
            Optional<CartItem> existingItemOpt = cart.getItems().stream()
                    .filter(item -> item.getProductId() == newItem.getProductId())
                    .findFirst();

            if (existingItemOpt.isPresent()) {
                // Increase quantity
                CartItem existingItem = existingItemOpt.get();
                existingItem.setQuantity(existingItem.getQuantity() + newItem.getQuantity());
            } else {
                // New item, set cart reference and add
                newItem.setCart(cart);
                cart.getItems().add(newItem);
            }
        }

        return cartRepository.save(cart);
    }


    //getCartByCartId
    public Optional<Cart> getCartByCartId(int id) {
        return cartRepository.findById(id);
    }
    //getCartByUserId
    public List<Cart> getCartByUserId(int userId) {
        return cartRepository.findByUserId(userId);
    }

    //Checkout from cart,
    /*getOrder,
    getOrderByUserId,
    getAllOrderbyRestaurantId,
    getAllOrdersForAdmin,
    getOrderTotal,
    updateCartByCartId,
    deleteOrder,
     */
}
