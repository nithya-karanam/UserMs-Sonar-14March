package com.infy.UserMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.infy.UserMS.dto.LoginDTO;
import com.infy.UserMS.dto.SellerDTO;
import com.infy.UserMS.service.SellerService;


@RestController
@CrossOrigin
public class SellerController {
	
	@Autowired
	SellerService sellerService;
	
	@Autowired
	Environment environment;
	
	@Value("${productDisableSeller.uri}")
	String productDisableSellerUri;
	
	@PostMapping(value = "/api/seller/register",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createSeller(@RequestBody SellerDTO sellerDTO) throws Exception {
		ResponseEntity<String> response = null;
		try {
	     	 sellerService.createSeller(sellerDTO);
			 String successMessage = environment.getProperty("API.SAVING_SUCCESSFUL");
			 response = new ResponseEntity<String>(successMessage,HttpStatus.CREATED);
				 
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.OK,environment.getProperty(e.getMessage()),e);
				 
		}
		return response;
			
			
	}
	@PostMapping(value = "/api/seller/login",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> Login(@RequestBody LoginDTO loginDTO) throws Exception {
		 ResponseEntity<String> response = null;
	     try {
		 boolean flag = sellerService.login(loginDTO);
		 if(flag) {
			String successMessage = environment.getProperty("LOGIN_SUCCESSFUL");
			response = new ResponseEntity<String>(successMessage,HttpStatus.OK);
		 }else {
			 String failureMessage = environment.getProperty("LOGIN_FAILED");
		     response = new ResponseEntity<String>(failureMessage,HttpStatus.BAD_REQUEST);
		 }
	     }catch(Exception e) {
	    	 throw new ResponseStatusException(HttpStatus.OK,environment.getProperty(e.getMessage()),e);
	     }
		 return response;
	}
	@GetMapping(value = "/api/seller",  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SellerDTO> getAllSellers() {
         return sellerService.sellerListAll();
	}
	@DeleteMapping(value = "/api/seller/delete")
	public ResponseEntity<String> inactivateSeller(@RequestBody SellerDTO sellerDTO){
		 ResponseEntity<String> response = null;
		// System.out.println(buyerDTO.getEmail());
		 boolean flag = sellerService.deleteSeller(sellerDTO.getEmail());
		 if(flag) {
			String successMessage = environment.getProperty("DELETION_SUCCESSFUL_SELLER");
			response = new ResponseEntity<String>(successMessage,HttpStatus.OK);
		 }else {
			 String failureMessage = environment.getProperty("SELLER_DOES_NOT_EXISTS");
		     response = new ResponseEntity<String>(failureMessage,HttpStatus.BAD_REQUEST);
		 }
		 return response;
	}
	@PutMapping(value = "/api/seller/deactivate",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deactivateSeller(@RequestBody SellerDTO sellerDTO){
		ResponseEntity<String> response = null;
		Integer Id = null;
		 Id = sellerService.deactivateSeller(sellerDTO.getEmail());
		 if(Id!=null) {
			String successMessage = environment.getProperty("DEACTIVATED_SELLER");
			String templateMessage = new RestTemplate().postForObject(productDisableSellerUri+Id, null, String.class, Id);
			response = new ResponseEntity<String>(successMessage+" and "+ templateMessage,HttpStatus.OK);
		 }else {
			 String failureMessage = environment.getProperty("ACCOUNT_ALREADY_DEACTIVATED");
		     response = new ResponseEntity<String>(failureMessage,HttpStatus.BAD_REQUEST);
		 }
		 return response;
	}
	

}
