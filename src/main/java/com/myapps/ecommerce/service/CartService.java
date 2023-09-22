package com.myapps.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myapps.ecommerce.entity.Cart;
import com.myapps.ecommerce.entity.CartItem;
import com.myapps.ecommerce.entity.Product;
import com.myapps.ecommerce.entity.Users;
import com.myapps.ecommerce.repository.CartItemRepository;
import com.myapps.ecommerce.repository.CartRepository;
import com.myapps.ecommerce.repository.ProductRepository;
import com.myapps.ecommerce.repository.UserRepository;

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

	public ResponseEntity<Cart> retrieveCartByUserId(int user_id) {
		Optional<Users> foundUserObj = userRepository.findById(user_id);
		if (foundUserObj.isPresent()) {
			Users foundUser = foundUserObj.get();
			Cart found = cartRepository.findByUser(foundUser);
			return ResponseEntity.ok(found);
		}
		return ResponseEntity.notFound().build();
	}

	public Cart addNewItemToCart(long cart_id, long pId, int quantity) {
		Cart cart = cartRepository.findById(cart_id).get();
		Product product = productRepository.findById(pId).get();

		for (CartItem cartItem : cart.getCartItems()) {
			if (cartItem.getProduct().equals(product)) {
				// If the product is already in the cart, update the quantity
				int newQuantity = quantity;
				cartItem.setQuantity(newQuantity);
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

	public ResponseEntity<Void> deleteItemFromCart(long cartId, long cartItemId) {
		Cart cart = cartRepository.findById(cartId).get();

		// Find the CartItem to delete
		CartItem cartItemToDelete = cart.getCartItems().stream().filter(item -> item.getId().equals(cartItemId))
				.findFirst().get();

		// Remove the CartItem from the Cart
		cart.getCartItems().remove(cartItemToDelete);
		cartItemRepository.delete(cartItemToDelete);
		cartRepository.save(cart);
		return ResponseEntity.noContent().build();
	}

}
