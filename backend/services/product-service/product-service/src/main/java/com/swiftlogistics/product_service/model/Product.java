package com.swiftlogistics.product_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer  productId;

    @Column(name = "user_id")
    private Integer  userId;

    @Column(name = "name")
    private String productName;

    @Column(name = "category")
    private String productCategory;

    @Column(name = "price")
    private Double productPrice;

    @Column(name = "stock_quantity")
    private Integer  productQuantity;

    @Column(name = "description")
    private String productDescription;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
