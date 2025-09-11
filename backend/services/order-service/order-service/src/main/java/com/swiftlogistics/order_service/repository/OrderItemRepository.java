package com.swiftlogistics.order_service.repository;


import com.swiftlogistics.order_service.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{
    Optional<OrderItem> findByItemId(int itemId);
}
