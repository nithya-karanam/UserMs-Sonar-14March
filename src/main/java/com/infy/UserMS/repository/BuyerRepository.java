package com.infy.UserMS.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import com.infy.UserMS.entity.BuyerEntity;


@Repository
public interface BuyerRepository  extends JpaRepository <BuyerEntity, Integer>{
	
	Optional<BuyerEntity> findByPhonenumber(String Phonenumber);
	Optional<BuyerEntity> findByEmail(String email);



}
