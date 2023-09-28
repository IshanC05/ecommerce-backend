package com.myapps.ecommerce.repository;

import com.myapps.ecommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    List<Address> findByUserId(Integer user_id);

}
