package com.myapps.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapps.ecommerce.entity.Cart;
import com.myapps.ecommerce.service.ServiceDao;

@RestController
public class CartController {

	@Autowired
	private ServiceDao serviceObj;

	@GetMapping(path = "/api/cart")
	public List<Cart> getAllCarts() {
		return serviceObj.retrieveAllCarts();
	}

}
