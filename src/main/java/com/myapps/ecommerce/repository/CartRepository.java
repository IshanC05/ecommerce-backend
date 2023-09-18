package com.myapps.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapps.ecommerce.entity.Cart;
import com.myapps.ecommerce.entity.Users;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByUser(Users user);
}
