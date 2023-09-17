package com.myapps.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapps.ecommerce.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
