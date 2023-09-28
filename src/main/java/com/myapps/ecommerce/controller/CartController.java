package com.myapps.ecommerce.controller;

import com.myapps.ecommerce.entity.Cart;
import com.myapps.ecommerce.entity.CartItem;
import com.myapps.ecommerce.exception.ApiResponse;
import com.myapps.ecommerce.security.JwtHelper;
import com.myapps.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JwtHelper helper;

    @GetMapping(path = "/carts")
    public List<Cart> getAllCarts() {
        return cartService.retrieveAllCarts();
    }

    @GetMapping(path = "/{userId}/carts")
    public ResponseEntity<Cart> getCartByUser(@PathVariable int userId, @RequestHeader HttpHeaders header) {
        String username = helper.getUsernameFromToken(header);
        return cartService.retrieveCartByUserId(userId, username);
    }

    @PostMapping(path = "/carts/{cart_id}/products/{pId}")
    public Cart addNewItemToCart(@PathVariable long cart_id, @PathVariable long pId, @RequestBody CartItem cart, @RequestHeader HttpHeaders header) {
        String username = helper.getUsernameFromToken(header);
        return cartService.addNewItemToCart(cart_id, pId, cart.getQuantity(), username);
    }

    @DeleteMapping(path = "/carts/{cart_id}/cartItem/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable long cart_id, @PathVariable long cartItemId, @RequestHeader HttpHeaders header) {
        String username = helper.getUsernameFromToken(header);
        return cartService.deleteItemFromCart(cart_id, cartItemId, username);
    }

}