package com.infy.UserMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import com.infy.UserMS.dto.WishListDTO;
import com.infy.UserMS.service.WishListService;

@RestController
@CrossOrigin
public class WishListController {
    
	@Autowired
	WishListService wishListService;
	
	@Autowired
	Environment environment;
	
	@PostMapping(value = "/api/wishlist/add",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addItemToWishList(@RequestBody WishListDTO wishListDTO) throws Exception {
		ResponseEntity<String> response = null;
		try {
	     	 wishListService.addItemToWishList(wishListDTO);
			 String successMessage = environment.getProperty("WISHLIST_ADDED");
			 response = new ResponseEntity<String>(successMessage,HttpStatus.CREATED);
				 
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.OK,environment.getProperty(e.getMessage()),e);
				 
		}
		return response;
			
			
	}
	@DeleteMapping(value = "/api/wishlist/remove")
	public ResponseEntity<String> deleteItemFromWishList(@RequestBody WishListDTO wishListDTO){
		 ResponseEntity<String> response = null;
		 boolean flag = wishListService.deleteItemFromWishList(wishListDTO);
		 if(flag) {
			String successMessage = environment.getProperty("DELETION_WISH_LIST");
			response = new ResponseEntity<String>(successMessage,HttpStatus.OK);
		 }else {
			 String failureMessage = environment.getProperty("WISHLIST_DOES_NOT_EXIST");
		     response = new ResponseEntity<String>(failureMessage,HttpStatus.BAD_REQUEST);
		 }
		 return response;
	}
	//Adds wish list to the cart
	@PostMapping(value = "/api/wishlist/cart")
	public ResponseEntity<String> addFromWishListToCart(@RequestBody WishListDTO wishListDTO){
		 ResponseEntity<String> response = null;
		 try {
		 wishListService.addFromWishListToCart(wishListDTO);
		 String successMessage = environment.getProperty("ADDED_WISH_LIST_CART");
		 response = new ResponseEntity<String>(successMessage,HttpStatus.OK);
		 }catch(Exception e) {
				throw new ResponseStatusException(HttpStatus.OK,environment.getProperty(e.getMessage()),e);
				 
		}
		 return response;
	}
}
