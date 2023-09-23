package com.myapps.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myapps.ecommerce.entity.Address;
import com.myapps.ecommerce.entity.Users;
import com.myapps.ecommerce.exception.ApiResponse;
import com.myapps.ecommerce.exception.ResourceNotFoundException;
import com.myapps.ecommerce.exception.UserNotFoundException;
import com.myapps.ecommerce.repository.AddressRepository;
import com.myapps.ecommerce.repository.UserRepository;

@Service
public class AddressService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	public List<Address> retrieveAllAddress() {
		return addressRepository.findAll();
	}

	public List<Address> retrieveAllAddressByUserId(Integer user_id) {
		Users user = userRepository.findById(user_id)
				.orElseThrow(() -> new UserNotFoundException("No matching user found"));
		return addressRepository.findByUserId(user_id);
	}

	public ResponseEntity<Address> addNewAddressByUserId(Integer user_id, Address address) {
		Users foundUser = userRepository.findById(user_id)
				.orElseThrow(() -> new UserNotFoundException("No matching user found"));
		address.setUser(foundUser);
		foundUser.getAddresses().add(address);
		userRepository.save(foundUser);
		return ResponseEntity.ok(address);
	}

	public ResponseEntity<ApiResponse> deleteAddressByUserId(Integer user_id, Integer add_id) {
		Users foundUser = userRepository.findById(user_id)
				.orElseThrow(() -> new UserNotFoundException("No matching user found"));
		Address addressToBeDeleted = addressRepository.findById(add_id)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "id", add_id));
		addressRepository.delete(addressToBeDeleted);
		ApiResponse apiResponse = new ApiResponse(String.format("Address with Id %s deleted successfully", add_id),
				true);
		return ResponseEntity.ok(apiResponse);
	}

	public ResponseEntity<Address> updateAddressById(Integer user_id, Integer add_id, Address newAddress) {
		Users foundUser = userRepository.findById(user_id)
				.orElseThrow(() -> new UserNotFoundException("No matching user found"));
		Address existingAddress = addressRepository.findById(add_id)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "id", add_id));
		if (newAddress.getStreet() != null)
			existingAddress.setStreet(newAddress.getStreet());
		if (newAddress.getCity() != null)
			existingAddress.setCity(newAddress.getCity());
		if (newAddress.getCountry() != null)
			existingAddress.setCountry(newAddress.getCountry());
		Address updatedAddress = addressRepository.save(existingAddress);
		return ResponseEntity.ok(updatedAddress);
	}
}