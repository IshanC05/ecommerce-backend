package com.myapps.ecommerce.controller;

import com.myapps.ecommerce.entity.Users;
import com.myapps.ecommerce.exception.ApiResponse;
import com.myapps.ecommerce.security.JwtHelper;
import com.myapps.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper helper;

    @GetMapping(path = "/users")
    public List<Users> getAllUsers(@RequestHeader HttpHeaders header) {
        String username = helper.getUsernameFromToken(header);
        return userService.retrieveAllUsers(username);
    }

    @GetMapping(path = "/users/{userId}")
    public ResponseEntity<Users> getUserByUserId(@RequestHeader HttpHeaders header, @PathVariable Integer userId) {
        String username = helper.getUsernameFromToken(header);
        return userService.retrieveUserById(userId, username);
    }

    @DeleteMapping(path = "/users/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@RequestHeader HttpHeaders header, @PathVariable Integer userId) {
        String username = helper.getUsernameFromToken(header);
        return userService.deleteUserById(userId, username);
    }

    @PutMapping(path = "/users/{userId}")
    public ResponseEntity<Users> updateUserByUserId(@RequestHeader HttpHeaders header, @PathVariable Integer userId,@RequestBody Users user) {
        String username = helper.getUsernameFromToken(header);
        return userService.updateUserById(userId, user, username);
    }

}