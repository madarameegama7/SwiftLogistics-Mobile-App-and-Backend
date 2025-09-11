package com.swiftlogistics.product_service.controller;

import com.swiftlogistics.product_service.model.Product;
import com.swiftlogistics.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import com.swiftlogistics.product_service.security.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final JwtUtil jwtUtil;

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String userId = jwtUtil.extractUserId(token);
            product.setUserId(Integer.parseInt(userId));
            return ResponseEntity.ok(productService.addProduct(product));
        }
        return ResponseEntity.badRequest().build();
    }


    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        return ResponseEntity.ok(productService.getProductById(id).orElseThrow());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vendor/{userId}")
    public ResponseEntity<List<Product>> getByVendor(@PathVariable int userId) {
        return ResponseEntity.ok(productService.getProductsByVendor(userId));
    }

    @GetMapping("/category/{productCategory}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable String productCategory){
        return ResponseEntity.ok(productService.getProductByCategory(productCategory));
    }
}
