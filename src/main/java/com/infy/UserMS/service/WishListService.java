package com.infy.UserMS.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.infy.UserMS.dto.WishListDTO;
import com.infy.UserMS.entity.CartEntity;
import com.infy.UserMS.entity.CartEntityUsingIdClass;
import com.infy.UserMS.entity.WishListEntity;
import com.infy.UserMS.entity.WishListEntityUsingIdClass;
import com.infy.UserMS.repository.CartRepository;
import com.infy.UserMS.repository.WishListRepository;

@Service
public class WishListService {
	
	@Autowired
	WishListRepository wishListRepo;
	
	@Autowired
	CartRepository cartRepo;
	
	public void addItemToWishList(WishListDTO wishListDTO) throws Exception{
		   WishListEntityUsingIdClass  wishListobj = new WishListEntityUsingIdClass();
		   wishListobj.setBuyerId(wishListDTO.getBuyerId());
		   wishListobj.setProdId(wishListDTO.getProdId());
		   Optional<WishListEntity> optWishList = wishListRepo.findById(wishListobj);
		 //  Optional<WishListEntity> optWishList = wishListRepo.findById(new WishListEntityUsingIdClass(wishListDTO.getBuyerId(),wishListDTO.getProdId()));
		   if(optWishList.isPresent()) {
			   throw new Exception("WISHLIST_EXISTS");
		   }
		   WishListEntity wishList = wishListDTO.createEntity();
		   wishListRepo.save(wishList);
	}
	public boolean deleteItemFromWishList(WishListDTO wishListDTO){
		WishListEntityUsingIdClass  wishListobj = new WishListEntityUsingIdClass();
		wishListobj.setBuyerId(wishListDTO.getBuyerId());
		wishListobj.setProdId(wishListDTO.getProdId());
		Optional<WishListEntity> optWishList = wishListRepo.findById(wishListobj);
	//	Optional<WishListEntity> optWishList = wishListRepo.findById(new WishListEntityUsingIdClass(wishListDTO.getBuyerId(),wishListDTO.getProdId()));
		if(optWishList.isPresent()) {
			WishListEntity wishList = optWishList.get();
			wishListRepo.delete(wishList);
			return true;
		}
		return false;
	}
	public void addFromWishListToCart(WishListDTO wishListDTO) throws Exception {
		WishListEntityUsingIdClass  wishListobj = new WishListEntityUsingIdClass();
		wishListobj.setBuyerId(wishListDTO.getBuyerId());
		wishListobj.setProdId(wishListDTO.getProdId());
		Optional<WishListEntity> optWishList = wishListRepo.findById(wishListobj);
		//Optional<WishListEntity> optWishList = wishListRepo.findById(new WishListEntityUsingIdClass(wishListDTO.getBuyerId(),wishListDTO.getProdId()));
		CartEntityUsingIdClass cartIdObject = new CartEntityUsingIdClass();
		cartIdObject.setBuyerId(wishListDTO.getBuyerId());
		cartIdObject.setProdId(wishListDTO.getProdId());
	    Optional<CartEntity> optCart = cartRepo.findById(cartIdObject);
		//Optional<CartEntity> optCart = cartRepo.findById(new CartEntityUsingIdClass(wishListDTO.getBuyerId(),wishListDTO.getProdId()));
		System.out.println(optCart.isPresent());
		System.out.println(optWishList.isPresent());
		if(optCart.isPresent() && optWishList.isPresent()) {
			WishListEntity wishList = optWishList.get();
			CartEntity cart = optCart.get();
			cart.setQuantity(cart.getQuantity()+1);
			wishListRepo.delete(wishList);
			cartRepo.save(cart);
		}
		else if(!optWishList.isPresent()) {
			throw new Exception("WISHLIST_EMPTY");
		}
		else {
			WishListEntity wishList = optWishList.get();
			wishListRepo.delete(wishList);
		    CartEntity cart = new CartEntity();
		    cart.setBuyerId(wishListDTO.getBuyerId());
		    cart.setProdId(wishListDTO.getProdId());
		    cart.setQuantity(1);
		    cartRepo.save(cart);
		}
		
	}
	
	

}
