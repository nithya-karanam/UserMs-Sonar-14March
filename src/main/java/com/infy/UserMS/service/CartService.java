package com.infy.UserMS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.infy.UserMS.dto.CartDTO;
import com.infy.UserMS.entity.CartEntity;
import com.infy.UserMS.entity.CartEntityUsingIdClass;
import com.infy.UserMS.repository.CartRepository;

@Service
public class CartService {
	
	@Autowired
	CartRepository cartRepo;
	
	public void addItemToCart(CartDTO cartDTO) throws Exception{
		CartEntityUsingIdClass cartIdObject = new CartEntityUsingIdClass();
		cartIdObject.setBuyerId(cartDTO.getBuyerId());
		cartIdObject.setProdId(cartDTO.getProdId());
	   Optional<CartEntity> optCart = cartRepo.findById(cartIdObject);
	   if(optCart.isPresent()) {
		   CartEntity cart = optCart.get();
		   int quantity = cart.getQuantity() + cartDTO.getQuantity();
		   cart.setQuantity(quantity);
		   cartRepo.save(cart);
	   }
	   else {
	      CartEntity cart = cartDTO.createEntity();
	      cartRepo.save(cart);
	   }
	}
	
	public boolean deleteItemFromCart(CartDTO cartDTO){
		CartEntityUsingIdClass cartIdObject = new CartEntityUsingIdClass();
		cartIdObject.setBuyerId(cartDTO.getBuyerId());
		cartIdObject.setProdId(cartDTO.getProdId());
	    Optional<CartEntity> optCart = cartRepo.findById(cartIdObject);
		
		if(optCart.isPresent()) {
			CartEntity cart = optCart.get();
			cartRepo.delete(cart);
			return true;
		}
		return false;
	}
	
	public CartDTO getItemsInCart(Integer prodId,Integer buyerId) throws Exception{
		CartEntityUsingIdClass cartIdObject = new CartEntityUsingIdClass();
		cartIdObject.setBuyerId(buyerId);
		cartIdObject.setProdId(prodId);
	   Optional<CartEntity> optCart = cartRepo.findById(cartIdObject);
		//Optional<CartEntity> optCart = cartRepo.findById(new CartEntityUsingIdClass(buyerId,prodId));
		if(optCart.isPresent()) {
			CartEntity cart=optCart.get();
			CartDTO cdto=CartDTO.valueOf(cart);
		return cdto;
		}
		else {
			throw new Exception("No such item available");
		}
	}
public List<CartDTO> getAllCartItems(Integer buyerId){
	List<CartEntity> carts = cartRepo.findByBuyerId(buyerId);
	List<CartDTO> cartDTOs = new ArrayList<>();

	for (CartEntity order:carts) {
		CartDTO cartDTO = CartDTO.valueOf(order);
		cartDTOs.add(cartDTO);
	}
	
	return cartDTOs;
}
public boolean deleteCartItem(Integer buyerId,Integer prodId){
	CartEntityUsingIdClass cartIdObject = new CartEntityUsingIdClass();
	cartIdObject.setBuyerId(buyerId);
	cartIdObject.setProdId(prodId);
    Optional<CartEntity> optCart = cartRepo.findById(cartIdObject);
	//Optional<CartEntity> optCart = cartRepo.findById(new CartEntityUsingIdClass(buyerId,prodId));
	if(optCart.isPresent()) {
		CartEntity cart = optCart.get();
		cartRepo.delete(cart);
		return true;
	}
	return false;
}
	

}
