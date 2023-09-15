package com.myapps.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myapps.ecommerce.entity.Users;
import com.myapps.ecommerce.service.ServiceDao;

@RestController
public class UserController {

	@Autowired
	private ServiceDao serviceObj;

	@GetMapping(path = "/api/users")
	public List<Users> getAllUsers() {
		return serviceObj.retrieveAllUsers();
	}

	@GetMapping(path = "/api/users/{id}")
	public ResponseEntity<Users> getUserById(@PathVariable Integer id) {
		return serviceObj.retrieveUserById(id);
	}

	@PostMapping(path = "/api/users")
	public ResponseEntity<Users> addNewUser(@RequestBody Users newUser) {
		return serviceObj.addNewUser(newUser);
	}

	@DeleteMapping(path = "/api/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
		return serviceObj.deleteUserById(id);
	}

	@PutMapping(path = "/api/users/{id}")
	public ResponseEntity<Users> updateUserById(@PathVariable Integer id, @RequestBody Users user) {
		return serviceObj.updateUserById(id, user);
	}

}
