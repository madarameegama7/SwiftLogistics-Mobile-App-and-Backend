package com.swiftlogistics.order_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.swiftlogistics.order_service.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    private int productId;
    private int quantity;
    private double unitPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
