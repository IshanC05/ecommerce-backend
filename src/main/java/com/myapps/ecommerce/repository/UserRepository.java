package com.myapps.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapps.ecommerce.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

}
