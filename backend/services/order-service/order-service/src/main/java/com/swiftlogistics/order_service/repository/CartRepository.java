package com.swiftlogistics.order_service.repository;

import com.swiftlogistics.order_service.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer>{
    List<Cart> findByUserId(int userId);
    List<Cart> findByCartId(int cartId);

}