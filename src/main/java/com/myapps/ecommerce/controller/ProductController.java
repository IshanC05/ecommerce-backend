package com.myapps.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myapps.ecommerce.entity.Product;
import com.myapps.ecommerce.exception.ApiResponse;
import com.myapps.ecommerce.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping(path = "/api/products")
	public List<Product> getAllProducts() {
		return productService.retrieveAllProducts();
	}

	@GetMapping(path = "/api/products/{pId}")
	public ResponseEntity<Product> getProductById(@PathVariable long pId) {
		return productService.retrieveProductById(pId);
	}

	@PostMapping(path = "/api/products")
	public ResponseEntity<Product> addNewProduct(@RequestBody Product newProduct) {
		return productService.addNewProduct(newProduct);
	}

	@PutMapping(path = "/api/products/{pId}")
	public ResponseEntity<Product> updateExistingProduct(@PathVariable long pId, @RequestBody Product newProduct) {
		return productService.updateProductById(pId, newProduct);
	}

	@DeleteMapping(path = "/api/products/{pId}")
	public ResponseEntity<ApiResponse> deleteAProduct(@PathVariable long pId) {
		return productService.deleteProductById(pId);
	}

}