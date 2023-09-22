package com.myapps.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myapps.ecommerce.entity.Product;
import com.myapps.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<Product> retrieveAllProducts() {
		return productRepository.findAll();
	}

	public ResponseEntity<Product> retrieveProductById(long id) {
		Optional<Product> opt = productRepository.findById(id);
		if (opt.isPresent()) {
			Product foundProduct = opt.get();
			return ResponseEntity.ok(foundProduct);
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<Product> addNewProduct(Product newProduct) {
		Product savedProduct = productRepository.save(newProduct);
		return ResponseEntity.status(201).body(savedProduct);
	}

	public ResponseEntity<Void> deleteProductById(long id) {
		Optional<Product> productToBeDeletedObj = productRepository.findById(id);
		if (productToBeDeletedObj.isPresent()) {
			Product productToBeDeleted = productToBeDeletedObj.get();
			productRepository.delete(productToBeDeleted);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<Product> updateProductById(long id, Product newProduct) {
		Optional<Product> productFoundObj = productRepository.findById(id);
		if (productFoundObj.isPresent()) {
			Product existingProduct = productFoundObj.get();

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
		return ResponseEntity.notFound().build();
	}
}
