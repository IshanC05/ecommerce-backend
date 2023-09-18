package com.myapps.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapps.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
