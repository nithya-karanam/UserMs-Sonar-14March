package com.infy.UserMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.infy.UserMS.dto.CartDTO;
import com.infy.UserMS.service.CartService;

@RestController
@CrossOrigin
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@Autowired
	Environment environment;
	//@Autowired
	//private KafkaTemplate<String, String> kafkaTemplate;
	//private static final String topic="TestTopic";
	
	@PostMapping(value = "/api/cart/add",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addItemToCart(@RequestBody CartDTO cartDTO) throws Exception {
		ResponseEntity<String> response = null;
		try {
	     	 cartService.addItemToCart(cartDTO);
			 String successMessage = environment.getProperty("CART_ADDED");
			 
			 response = new ResponseEntity<String>(successMessage,HttpStatus.CREATED);
				 
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.OK,environment.getProperty(e.getMessage()),e);
				 
		}
		return response;
			
			
	}
	@DeleteMapping(value = "/api/cart/remove")
	public ResponseEntity<String> deleteItemFromCart(@RequestBody CartDTO cartDTO){
		 ResponseEntity<String> response = null;
		 boolean flag = cartService.deleteItemFromCart(cartDTO);
		 if(flag) {
			String successMessage = environment.getProperty("DELETION_CART");
			response = new ResponseEntity<String>(successMessage,HttpStatus.OK);
		 }else {
			 String failureMessage = environment.getProperty("CART_DOES_NOT_EXIST");
		     response = new ResponseEntity<String>(failureMessage,HttpStatus.BAD_REQUEST);
		 }
		 return response;
	}
	//@PostMapping(value="/cart/item/{buyerId}/{prodId}")
	//public String publish(@PathVariable Integer buyerId,@PathVariable Integer prodId) throws Exception{
	//String message=null;
	//CartDTO cartdto=cartService.getItemsInCart(prodId, buyerId);
	//message=cartdto.getBuyerId()+" "+cartdto.getProdId()+" "+cartdto.getQuantity();
	//	kafkaTemplate.send(topic,message);
		//return "published";
	//}
	@GetMapping(value="/cart/item/{buyerId}/{prodId}")
	public CartDTO getspecificItem(@PathVariable Integer buyerId,@PathVariable Integer prodId) throws Exception{

		return cartService.getItemsInCart(prodId, buyerId);
	}
	@GetMapping(value="/cart/items/{buyerId}")
	public List<CartDTO> getAllCartItems(@PathVariable Integer buyerId){
		return cartService.getAllCartItems(buyerId);
	}
	@DeleteMapping(value="/cart/remove/{buyerId}/{prodId}")
	public ResponseEntity<String> deleteItemFromCart(@PathVariable Integer buyerId,@PathVariable Integer prodId){
		 ResponseEntity<String> response = null;
		 boolean flag = cartService.deleteCartItem(buyerId, prodId);
		 if(flag) {
			String successMessage = environment.getProperty("DELETION_CART");
			response = new ResponseEntity<String>(successMessage,HttpStatus.OK);
		 }else {
			 String failureMessage = environment.getProperty("CART_DOES_NOT_EXIST");
		     response = new ResponseEntity<String>(failureMessage,HttpStatus.BAD_REQUEST);
		 }
		 return response;
	}
	

}

