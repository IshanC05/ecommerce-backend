package com.myapps.ecommerce.controller;

import com.myapps.ecommerce.entity.Product;
import com.myapps.ecommerce.exception.ApiResponse;
import com.myapps.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(path = "/products")
    public List<Product> getAllProducts() {
        return productService.retrieveAllProducts();
    }

    @GetMapping(path = "/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable long productId) {
        return productService.retrieveProductById(productId);
    }

    @PostMapping(path = "/products")
    public ResponseEntity<Product> addNewProduct(@RequestBody Product newProduct) {
        return productService.addNewProduct(newProduct);
    }

    @PutMapping(path = "/products/{productId}")
    public ResponseEntity<Product> updateExistingProduct(@PathVariable long productId, @RequestBody Product newProduct) {
        return productService.updateProductById(productId, newProduct);
    }

    @DeleteMapping(path = "/products/{productId}")
    public ResponseEntity<ApiResponse> deleteAProduct(@PathVariable long productId) {
        return productService.deleteProductById(productId);
    }

}