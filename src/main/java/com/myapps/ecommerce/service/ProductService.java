package com.myapps.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myapps.ecommerce.entity.Product;
import com.myapps.ecommerce.exception.ApiResponse;
import com.myapps.ecommerce.exception.ResourceNotFoundException;
import com.myapps.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<Product> retrieveAllProducts() {
		return productRepository.findAll();
	}

	public ResponseEntity<Product> retrieveProductById(long id) {
		Product foundProduct = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Id", id));
		return ResponseEntity.ok(foundProduct);

	}

	public ResponseEntity<Product> addNewProduct(Product newProduct) {
		Product savedProduct = productRepository.save(newProduct);
		return ResponseEntity.status(201).body(savedProduct);
	}

	public ResponseEntity<ApiResponse> deleteProductById(long id) {
		Product productToBeDeleted = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Id", id));
		productRepository.delete(productToBeDeleted);
		ApiResponse apiResponse = new ApiResponse(
				String.format("Product with Id %s deleted successfully", id), true);
		return ResponseEntity.ok(apiResponse);
	}

	public ResponseEntity<Product> updateProductById(long id, Product newProduct) {
		Product existingProduct = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "Id", id));

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