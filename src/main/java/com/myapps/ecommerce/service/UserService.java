package com.myapps.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myapps.ecommerce.entity.Cart;
import com.myapps.ecommerce.entity.Users;
import com.myapps.ecommerce.exception.ApiResponse;
import com.myapps.ecommerce.exception.UserNotFoundException;
import com.myapps.ecommerce.payload.UserLoginDto;
import com.myapps.ecommerce.payload.UserSignUpDto;
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
		Users foundUser = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("No matching user found"));
		return ResponseEntity.ok(foundUser);
	}

	public ResponseEntity<Users> addNewUser(UserSignUpDto newUserDto) {
		Users newUser = new Users();
		newUser.setName(newUserDto.getName());
		newUser.setEmail(newUserDto.getEmail());
		newUser.setPassword(newUserDto.getPassword());
		Users savedUser = userRepository.save(newUser);
		Cart newCart = new Cart();
		newCart.setUser(savedUser);
		cartRepository.save(newCart);
		return ResponseEntity.status(201).body(savedUser);
	}

	public ResponseEntity<Users> retrieveUserForLogin(UserLoginDto userDto) {
		Users foundUser = userRepository.findByEmail(userDto.getEmail())
				.orElseThrow(() -> new UserNotFoundException("Invalid Credentials"));
		return ResponseEntity.ok(foundUser);
	}

	public ResponseEntity<ApiResponse> deleteUserById(int user_id) {
		Users userToBeDeleted = userRepository.findById(user_id)
				.orElseThrow(() -> new UserNotFoundException("No matching user found"));
		userRepository.delete(userToBeDeleted);
		ApiResponse apiResponse = new ApiResponse(String.format("User with Id %s deleted successfully", user_id), true);
		return ResponseEntity.ok(apiResponse);
	}

	public ResponseEntity<Users> updateUserById(int id, Users user) {
		Users existingUser = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("No matching user found"));
		if (user.getEmail() != null)
			existingUser.setEmail(user.getEmail());
		if (user.getName() != null)
			existingUser.setName(user.getName());
		if (user.getPassword() != null)
			existingUser.setPassword(user.getPassword());
		Users updatedUser = userRepository.save(existingUser);
		return ResponseEntity.ok(updatedUser);
	}

}