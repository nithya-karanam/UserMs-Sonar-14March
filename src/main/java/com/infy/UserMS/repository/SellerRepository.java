package com.infy.UserMS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infy.UserMS.entity.SellerEntity;

@Repository
public interface SellerRepository extends JpaRepository<SellerEntity, Integer> {
	
	Optional<SellerEntity> findByPhonenumber(String Phonenumber);
	Optional<SellerEntity> findByEmail(String email);
	

}
