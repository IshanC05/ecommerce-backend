package com.myapps.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.myapps.ecommerce.entity.Address;
import com.myapps.ecommerce.entity.Users;
import com.myapps.ecommerce.repository.AddressRepository;
import com.myapps.ecommerce.repository.UserRepository;

@Component
public class ServiceDao {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

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

}
