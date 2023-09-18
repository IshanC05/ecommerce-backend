package com.myapps.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.myapps.ecommerce.entity.Address;
import com.myapps.ecommerce.entity.Cart;
import com.myapps.ecommerce.entity.CartItem;
import com.myapps.ecommerce.entity.Product;
import com.myapps.ecommerce.entity.Users;
import com.myapps.ecommerce.repository.AddressRepository;
import com.myapps.ecommerce.repository.CartItemRepository;
import com.myapps.ecommerce.repository.CartRepository;
import com.myapps.ecommerce.repository.ProductRepository;
import com.myapps.ecommerce.repository.UserRepository;

@Component
public class ServiceDao {

	private UserRepository userRepository;
	private AddressRepository addressRepository;
	private CartRepository cartRepository;
	private ProductRepository productRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	public ServiceDao(UserRepository userRepository, AddressRepository addressRepository, CartRepository cartRepository,
			ProductRepository productRepository) {
		super();
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
	}

	// Users

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

	// Address

	public List<Address> retrieveAllAddress() {
		return addressRepository.findAll();
	}

	public List<Address> retrieveAllAddressByUserId(Integer user_id) {
		return addressRepository.findByUserId(user_id);
	}

	public ResponseEntity<Address> addNewAddressByUserId(Integer user_id, Address address) {
		Optional<Users> userFoundObj = userRepository.findById(user_id);
		if (userFoundObj.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Users foundUser = userFoundObj.get();
		address.setUser(foundUser);
		foundUser.getAddresses().add(address);
		userRepository.save(foundUser);
		return ResponseEntity.ok(address);
	}

	public ResponseEntity<Void> deleteAddressByUserId(Integer user_id, Integer add_id) {
		Optional<Users> userFoundObj = userRepository.findById(user_id);
		if (userFoundObj.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Optional<Address> addressToBeDeletedObj = addressRepository.findById(add_id);
		if (addressToBeDeletedObj.isPresent()) {
			addressRepository.delete(addressToBeDeletedObj.get());
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<Address> updateAddressById(Integer user_id, Integer add_id, Address newAddress) {
		Optional<Users> userFoundObj = userRepository.findById(user_id);
		if (userFoundObj.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Optional<Address> addressToBeUpdatedObj = addressRepository.findById(add_id);
		if (addressToBeUpdatedObj.isPresent()) {
			Address existingAddress = addressToBeUpdatedObj.get();
			if (newAddress.getStreet() != null)
				existingAddress.setStreet(newAddress.getStreet());
			if (newAddress.getCity() != null)
				existingAddress.setCity(newAddress.getCity());
			if (newAddress.getCountry() != null)
				existingAddress.setCountry(newAddress.getCountry());
			Address updatedAddress = addressRepository.save(existingAddress);
			return ResponseEntity.ok(updatedAddress);
		}
		return ResponseEntity.notFound().build();
	}

	// Cart
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

//	public Cart addNewItemToCart(long cart_id, long pId) {
//		Cart cart = cartRepository.findById(cart_id).get();
//		Product p = productRepository.findById(pId).get();
//		Set<Product> allProd = cart.getProduct();
//		allProd.add(p);
//		cart.setProduct(allProd);
//		return cartRepository.save(cart);
//	}

	// Product
	public List<Product> retrieveAllProducts() {
		return productRepository.findAll();
	}

	public ResponseEntity<Product> retrieveProductById(long id) {
		Optional<Product> opt = productRepository.findById(id);
		if (opt.isPresent()) {
			Product foundProduct = opt.get();
			return ResponseEntity.ok(foundProduct);
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<Product> addNewProduct(Product newProduct) {
		Product savedProduct = productRepository.save(newProduct);
		return ResponseEntity.status(201).body(savedProduct);
	}

	public ResponseEntity<Void> deleteProductById(long id) {
		Optional<Product> productToBeDeletedObj = productRepository.findById(id);
		if (productToBeDeletedObj.isPresent()) {
			Product productToBeDeleted = productToBeDeletedObj.get();
			productRepository.delete(productToBeDeleted);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<Product> updateProductById(long id, Product newProduct) {
		Optional<Product> productFoundObj = productRepository.findById(id);
		if (productFoundObj.isPresent()) {
			Product existingProduct = productFoundObj.get();

			if (newProduct.getProductName() != null)
				existingProduct.setProductName(newProduct.getProductName());

			if (newProduct.getDescription() != null)
				existingProduct.setDescription(newProduct.getDescription());

			if (newProduct.getImgUrl() != null)
				existingProduct.setImgUrl(newProduct.getImgUrl());

			if (newProduct.getPrice() != 0.0d)
				existingProduct.setPrice(newProduct.getPrice());

			Product updatedProduct = productRepository.save(existingProduct);
			return ResponseEntity.ok(updatedProduct);
		}
		return ResponseEntity.notFound().build();
	}

}
