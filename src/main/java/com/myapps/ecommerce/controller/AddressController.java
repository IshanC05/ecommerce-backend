package com.myapps.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myapps.ecommerce.entity.Address;
import com.myapps.ecommerce.service.ServiceDao;

@RestController
public class AddressController {

	@Autowired
	private ServiceDao serviceObj;

	@GetMapping(path = "/api/address")
	public List<Address> getAllAddress() {
		return serviceObj.retrieveAllAddress();
	}

	@GetMapping(path = "/api/{user_id}/address")
	public List<Address> retrieveAllAddressByUserId(@PathVariable Integer user_id) {
		return serviceObj.retrieveAllAddressByUserId(user_id);
	}

	@PostMapping(path = "/api/{user_id}/address")
	public ResponseEntity<Address> addNewAddress(@PathVariable Integer user_id, @RequestBody Address address) {
		return serviceObj.addNewAddressByUserId(user_id, address);
	}

	@DeleteMapping(path = "/api/{user_id}/address/{add_id}")
	public ResponseEntity<Void> deleteAnAddress(@PathVariable Integer user_id, @PathVariable Integer add_id) {
		return serviceObj.deleteAddressByUserId(user_id, add_id);
	}

	@PutMapping(path = "/api/{user_id}/address/{add_id}")
	public ResponseEntity<Address> updateAnAddress(@PathVariable Integer user_id, @PathVariable Integer add_id,
			@RequestBody Address address) {
		return serviceObj.updateAddressById(user_id, add_id, address);
	}

}
