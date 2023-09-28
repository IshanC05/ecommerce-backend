package com.myapps.ecommerce.repository;

import com.myapps.ecommerce.entity.Cart;
import com.myapps.ecommerce.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUser(Users user);
}
