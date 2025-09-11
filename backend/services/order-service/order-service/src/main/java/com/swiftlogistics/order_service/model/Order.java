package com.swiftlogistics.order_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import com.swiftlogistics.order_service.enums.OrderStatus;
import com.swiftlogistics.order_service.enums.PaymentStatus;
import com.swiftlogistics.order_service.enums.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@Getter
@Setter

public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int orderId;

    protected int userId;

    protected double totalAmount;

    protected LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> items;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private String assignedDriver;

    public void setOrderItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setTotal(double total) {
        this.totalAmount = total;
    }



}