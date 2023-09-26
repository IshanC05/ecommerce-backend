package com.myapps.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class Test {

	@GetMapping(path = "/test")
	public String testEndpoint() {
		return "Test working";
	}
}
