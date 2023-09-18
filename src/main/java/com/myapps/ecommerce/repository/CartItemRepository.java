package com.myapps.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapps.ecommerce.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
