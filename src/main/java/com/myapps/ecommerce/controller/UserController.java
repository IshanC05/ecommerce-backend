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
import com.myapps.ecommerce.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(path = "/api/users")
	public List<Users> getAllUsers() {
		return userService.retrieveAllUsers();
	}

	@GetMapping(path = "/api/users/{id}")
	public ResponseEntity<Users> getUserById(@PathVariable Integer id) {
		return userService.retrieveUserById(id);
	}

	@PostMapping(path = "/api/users")
	public ResponseEntity<Users> addNewUser(@RequestBody Users newUser) {
		return userService.addNewUser(newUser);
	}

	@PostMapping(path = "/api/users/login")
	public ResponseEntity<Users> getUserDetailsForLogin(@RequestBody Users newUser) {
		return userService.retrieveUserForLogin(newUser);
	}

	@DeleteMapping(path = "/api/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
		return userService.deleteUserById(id);
	}

	@PutMapping(path = "/api/users/{id}")
	public ResponseEntity<Users> updateUserById(@PathVariable Integer id, @RequestBody Users user) {
		return userService.updateUserById(id, user);
	}

}
