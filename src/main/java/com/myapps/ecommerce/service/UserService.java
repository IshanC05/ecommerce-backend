package com.myapps.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myapps.ecommerce.entity.Cart;
import com.myapps.ecommerce.entity.Users;
import com.myapps.ecommerce.repository.CartRepository;
import com.myapps.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	public List<Users> retrieveAllUsers() {
		return userRepository.findAll();
	}

	public ResponseEntity<Users> retrieveUserById(int id) {
		Optional<Users> opt = userRepository.findById(id);
		if (opt.isPresent()) {
			Users foundUser = opt.get();
			return ResponseEntity.ok(foundUser);
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<Users> addNewUser(Users newUser) {
		Users savedUser = userRepository.save(newUser);
		Cart newCart = new Cart();
		newCart.setUser(savedUser);
		cartRepository.save(newCart);
		return ResponseEntity.status(201).body(savedUser);
	}

	public ResponseEntity<Void> deleteUserById(int id) {
		Optional<Users> userToBeDeletedObj = userRepository.findById(id);
		if (userToBeDeletedObj.isPresent()) {
			userRepository.delete(userToBeDeletedObj.get());
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<Users> updateUserById(int id, Users user) {
		Optional<Users> existingUserObj = userRepository.findById(id);
		if (existingUserObj.isPresent()) {
			Users existingUser = existingUserObj.get();
			if (user.getEmail() != null)
				existingUser.setEmail(user.getEmail());
			if (user.getName() != null)
				existingUser.setName(user.getName());
			if (user.getPassword() != null)
				existingUser.setPassword(user.getPassword());
			Users updatedUser = userRepository.save(existingUser);
			return ResponseEntity.ok(updatedUser);
		}
		return ResponseEntity.notFound().build();
	}

}
