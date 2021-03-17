package com.infy.UserMS.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infy.UserMS.entity.CartEntity;
import com.infy.UserMS.entity.CartEntityUsingIdClass;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,CartEntityUsingIdClass> {
	List<CartEntity> findByBuyerId(int buyerId);
}
