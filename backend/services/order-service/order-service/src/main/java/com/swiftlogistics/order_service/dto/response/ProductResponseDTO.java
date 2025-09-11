package com.swiftlogistics.order_service.dto.response;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private int productId;
    private String productName;
    private String productCategory;
    private double productPrice;
    private int productQuantity;
    private String productDescription;
}
