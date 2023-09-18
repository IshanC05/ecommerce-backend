package com.myapps.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapps.ecommerce.entity.Cart;
import com.myapps.ecommerce.service.ServiceDao;

@RestController
public class CartController {

	@Autowired
	private ServiceDao serviceObj;

	@GetMapping(path = "/api/carts")
	public List<Cart> getAllCarts() {
		return serviceObj.retrieveAllCarts();
	}

	@GetMapping(path = "/api/{user_id}/carts")
	public ResponseEntity<Cart> getCartByUser(@PathVariable int user_id) {
		return serviceObj.retrieveCartByUserId(user_id);
	}

	@PostMapping(path = "/api/carts/{cart_id}/products/{pId}")
	public Cart addNewItemToCart(@PathVariable long cart_id, @PathVariable long pId) {
		return serviceObj.addNewItemToCart(cart_id, pId);
	}

}
