package com.myapps.ecommerce.service;

import com.myapps.ecommerce.entity.Cart;
import com.myapps.ecommerce.entity.CartItem;
import com.myapps.ecommerce.entity.Product;
import com.myapps.ecommerce.entity.Users;
import com.myapps.ecommerce.exception.ApiResponse;
import com.myapps.ecommerce.exception.ResourceNotFoundException;
import com.myapps.ecommerce.exception.UserMismatchException;
import com.myapps.ecommerce.exception.UserNotFoundException;
import com.myapps.ecommerce.repository.CartItemRepository;
import com.myapps.ecommerce.repository.CartRepository;
import com.myapps.ecommerce.repository.ProductRepository;
import com.myapps.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> retrieveAllCarts() {
        return cartRepository.findAll();
    }

    public ResponseEntity<Cart> retrieveCartByUserId(int userId, String username) {
        Users foundUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("No matching user found"));
        if (!foundUser.getUsername().equals(username))
            throw new UserMismatchException("Logged In user and requesting user is different");
        Cart found = cartRepository.findByUser(foundUser);
        return ResponseEntity.ok(found);
    }

    public Cart addNewItemToCart(long cartId, long productId, int quantity, String username) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        Users foundUser = userRepository.findById(cart.getUser().getId()).orElseThrow(() -> new UserNotFoundException("No " +
                "matching " +
                "user found"));
        if (!foundUser.getUsername().equals(username))
            throw new UserMismatchException("Logged In user and requesting user is different");

        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getProduct().equals(product)) {
                // If the product is already in the cart, update the quantity
                cartItem.setQuantity(quantity);
                return cartRepository.save(cart);
            }
        }
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cart.getCartItems().add(cartItem);
        for (CartItem i : cart.getCartItems()) {
            System.out.println(i);
        }
        cartItemRepository.save(cartItem);
        return cartRepository.save(cart);
    }

    public ResponseEntity<ApiResponse> deleteItemFromCart(long cartId, long cartItemId, String username) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart", "id", cartId));

        // Find the CartItem to delete
        CartItem cartItemToDelete = cart.getCartItems().stream().filter(item -> item.getId().equals(cartItemId)).findFirst().get();

        // Remove the CartItem from the Cart
        cart.getCartItems().remove(cartItemToDelete);
        cartItemRepository.delete(cartItemToDelete);
        cartRepository.save(cart);
        ApiResponse apiResponse = new ApiResponse(String.format("Cart Item with Id %s deleted successfully", cartItemId), true);
        return ResponseEntity.ok(apiResponse);
    }

}