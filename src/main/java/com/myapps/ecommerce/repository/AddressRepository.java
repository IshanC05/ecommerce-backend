package com.myapps.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapps.ecommerce.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
	
	List<Address> findByUserId(Integer user_id);
	
}
