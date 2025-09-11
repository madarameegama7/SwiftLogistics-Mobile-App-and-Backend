package com.swiftlogistics.product_service.service;

import com.swiftlogistics.product_service.model.Product;
import com.swiftlogistics.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService{
    private final ProductRepository productRepository;

    public Product addProduct(Product product){

        return productRepository.save(product);

    }
    public List<Product> getAllProducts(){

        return productRepository.findAll();
    }

    public Optional<Product> getProductById(int id){
        return productRepository.findById(id);
    }
    public List<Product> getProductsByVendor(int userId){
        return productRepository.findByUserId(userId);
    }
    public List<Product> getProductByCategory(String productCategory){
        return productRepository.findByProductCategory(productCategory);
    }
    public void deleteProduct(int id){

        productRepository.deleteById(id);
    }
    public Product updateProduct(int id, Product updatedProduct){
        Product existing = productRepository.findById(id).orElseThrow();

        if (updatedProduct.getProductName() != null) {
            existing.setProductName(updatedProduct.getProductName());
        }

        if (updatedProduct.getProductCategory() != null) {
            existing.setProductCategory(updatedProduct.getProductCategory());
        }

        if (updatedProduct.getProductPrice() != null) {
            existing.setProductPrice(updatedProduct.getProductPrice());
        }

        if (updatedProduct.getProductQuantity() != null) {
            existing.setProductQuantity(updatedProduct.getProductQuantity());
        }

        if (updatedProduct.getProductDescription() != null) {
            existing.setProductDescription(updatedProduct.getProductDescription());
        }

        return productRepository.save(existing);

    }


}