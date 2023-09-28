package com.myapps.ecommerce.service;

import com.myapps.ecommerce.entity.Cart;
import com.myapps.ecommerce.entity.Users;
import com.myapps.ecommerce.exception.ApiResponse;
import com.myapps.ecommerce.exception.UserMismatchException;
import com.myapps.ecommerce.exception.UserNotFoundException;
import com.myapps.ecommerce.payload.UserSignUpDto;
import com.myapps.ecommerce.repository.CartRepository;
import com.myapps.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Users> retrieveAllUsers(String username) {
        return userRepository.findAll();
    }

    public ResponseEntity<Users> retrieveUserById(int userId, String username) {
        Users foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No matching user found"));
        if (!foundUser.getUsername().equals(username))
            throw new UserMismatchException("Logged In user and requesting user is different");
        return ResponseEntity.ok(foundUser);
    }

    public Users addNewUser(UserSignUpDto newUserDto) {
        Users newUser = new Users();
        newUser.setName(newUserDto.getName());
        newUser.setEmail(newUserDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        Users savedUser = userRepository.save(newUser);
        Cart newCart = new Cart();
        newCart.setUser(savedUser);
        cartRepository.save(newCart);
        return savedUser;
    }

    public ResponseEntity<ApiResponse> deleteUserById(int userId, String username) {
        Users userToBeDeleted = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No matching user found"));
        if (!userToBeDeleted.getUsername().equals(username))
            throw new UserMismatchException("Logged In user and requesting user is different");
        userRepository.delete(userToBeDeleted);
        ApiResponse apiResponse = new ApiResponse(String.format("User with Id %s deleted successfully", userId), true);
        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<Users> updateUserById(int userId, Users user, String username) {
        Users existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No matching user found"));
        if (!existingUser.getUsername().equals(username))
            throw new UserMismatchException("Logged In user and requesting user is different");
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