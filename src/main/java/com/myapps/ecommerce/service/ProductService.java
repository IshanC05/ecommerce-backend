package com.myapps.ecommerce.service;

import com.myapps.ecommerce.entity.Product;
import com.myapps.ecommerce.exception.ApiResponse;
import com.myapps.ecommerce.exception.ResourceNotFoundException;
import com.myapps.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> retrieveAllProducts() {
        return productRepository.findAll();
    }

    public ResponseEntity<Product> retrieveProductById(long productId) {
        Product foundProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));
        return ResponseEntity.ok(foundProduct);

    }

    public ResponseEntity<Product> addNewProduct(Product newProduct) {
        Product savedProduct = productRepository.save(newProduct);
        return ResponseEntity.status(201).body(savedProduct);
    }

    public ResponseEntity<ApiResponse> deleteProductById(long productId) {
        Product productToBeDeleted = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));
        productRepository.delete(productToBeDeleted);
        ApiResponse apiResponse = new ApiResponse(
                String.format("Product with Id %s deleted successfully", productId), true);
        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<Product> updateProductById(long productId, Product newProduct) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "Id", productId));

        if (newProduct.getProductName() != null)
            existingProduct.setProductName(newProduct.getProductName());

        if (newProduct.getDescription() != null)
            existingProduct.setDescription(newProduct.getDescription());

        if (newProduct.getImgUrl() != null)
            existingProduct.setImgUrl(newProduct.getImgUrl());

        if (newProduct.getPrice() != 0.0d)
            existingProduct.setPrice(newProduct.getPrice());

        Product updatedProduct = productRepository.save(existingProduct);
        return ResponseEntity.ok(updatedProduct);

    }
}