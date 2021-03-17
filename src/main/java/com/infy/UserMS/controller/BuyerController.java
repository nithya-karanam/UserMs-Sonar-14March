package com.infy.UserMS.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;


import com.infy.UserMS.dto.BuyerDTO;
import com.infy.UserMS.dto.LoginDTO;
import com.infy.UserMS.dto.OrderDTO;
import com.infy.UserMS.dto.ProductDTO;
import com.infy.UserMS.service.BuyerService;


@RestController
@CrossOrigin
public class BuyerController {
	
	@Autowired
	BuyerService buyerService;
	
	@Autowired
	Environment environment;
	
	@Value("${product.uri}")
	String productUri;
	
	@Value("${order.uri}")
	String orderUri;
	
	@Value("${orderPost.uri}")
	String orderPostUri;
	
	
	
	@PostMapping(value = "/api/buyer/register",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createBuyer(@RequestBody BuyerDTO buyerDTO) throws Exception {
		ResponseEntity<String> response = null;
		try {
	     	 buyerService.createBuyer(buyerDTO);
			 String successMessage = environment.getProperty("API.SAVING_SUCCESSFUL");
			 response = new ResponseEntity<String>(successMessage,HttpStatus.CREATED);
				 
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.OK,environment.getProperty(e.getMessage()),e);
				 
		}
		return response;
			
			
	}
		@PostMapping(value = "/api/buyer/login",  consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<String> Login(@RequestBody LoginDTO loginDTO) throws Exception {
			 ResponseEntity<String> response = null;
		     try {
			     boolean flag = buyerService.login(loginDTO);
			     if(flag) {
				     String successMessage = environment.getProperty("LOGIN_SUCCESSFUL");
				     response = new ResponseEntity<String>(successMessage,HttpStatus.OK);
			     }else {
				     String failureMessage = environment.getProperty("LOGIN_FAILED");
			         response = new ResponseEntity<String>(failureMessage,HttpStatus.BAD_REQUEST);
			      }
			 }catch (Exception e) {
				 throw new ResponseStatusException(HttpStatus.OK,environment.getProperty(e.getMessage()),e);
			 }
			 return response;
			
			
		}
		@GetMapping(value = "/api/buyer",  produces = MediaType.APPLICATION_JSON_VALUE)
		public List<BuyerDTO> getAllBuyers() {
             return buyerService.buyerListAll();
		}
		@DeleteMapping(value = "/api/buyer/delete")
		public ResponseEntity<String> deleteBuyer(@RequestBody BuyerDTO buyerDTO){
			 ResponseEntity<String> response = null;
			// System.out.println(buyerDTO.getEmail());
			 boolean flag = buyerService.deleteBuyer(buyerDTO.getEmail());
			 if(flag) {
				String successMessage = environment.getProperty("DELETION_SUCCESSFUL_BUYER");
				response = new ResponseEntity<String>(successMessage,HttpStatus.OK);
			 }else {
				 String failureMessage = environment.getProperty("BUYER_DOES_NOT_EXISTS");
			     response = new ResponseEntity<String>(failureMessage,HttpStatus.BAD_REQUEST);
			 }
			 return response;
		}
		
		@PutMapping(value = "/api/buyer/deactivate",consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<String> deactivateBuyer(@RequestBody BuyerDTO buyerDTO){
			ResponseEntity<String> response = null;
			 boolean flag = buyerService.deactivateBuyer(buyerDTO.getEmail());
			 if(flag) {
				String successMessage = environment.getProperty("DEACTIVATED_BUYER");
				response = new ResponseEntity<String>(successMessage,HttpStatus.OK);
			 }else {
				 String failureMessage = environment.getProperty("ACCOUNT_ALREADY_DEACTIVATED");
			     response = new ResponseEntity<String>(failureMessage,HttpStatus.BAD_REQUEST);
			 }
			 return response;
		}
		//Gets the required products in the cart list
		@GetMapping(value = "/api/buyer/{buyerId}/cart/products",  consumes = MediaType.APPLICATION_JSON_VALUE)
		public List<ProductDTO> getBuyerCartProducts(@PathVariable int buyerId) {
			List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
			List<Integer> prodIdList = buyerService.getProductIdsFromCart(buyerId);
			for(Integer Id:prodIdList) {
				ProductDTO productDTO=new RestTemplate().getForObject(productUri+"products/"+Id, ProductDTO.class);
				
				productDTOList.add(productDTO);
			}
            return productDTOList;
		}
		//Modifies the amount based on reward points
		@GetMapping(value = "/api/buyer/amountrewardpoints/{buyerId}",produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<String> modifiedAmountWithRewardPoints(@PathVariable int buyerId)  {
			 ResponseEntity<String> response = null;
			// System.out.println("Start of the process");
			 try {
			 double amount = new RestTemplate().getForObject(orderUri+buyerId, double.class);
			// System.out.println(amount);
		     if(amount == -1) {
			    String failureMessage = environment.getProperty("BUYER_DOES_NOT_EXISTS");
			    response = new ResponseEntity<String>(failureMessage,HttpStatus.BAD_REQUEST);
			}else {
				double number = buyerService.getModifiedAmount(amount, buyerId);
				if (number == -1) {
					String failureMessage = environment.getProperty("BUYER_DOES_NOT_EXISTS");
				    response = new ResponseEntity<String>(failureMessage,HttpStatus.BAD_REQUEST);
				}
			//	System.out.println("Working till hashmap");
			//	System.out.println(number);
				Map<String, String> params = new HashMap<String, String>();
			    params.put("buyerId", String.valueOf(buyerId));
			    OrderDTO orderDTO = new OrderDTO();
			    orderDTO.setAmount(number);
			    System.out.println("Working till hashmap");
	            new RestTemplate().put(orderPostUri+buyerId, orderDTO, params);
				String successMessage = environment.getProperty("AMOUNT_CHANGED_SUCCESSFULLY");
				response = new ResponseEntity<String>(successMessage,HttpStatus.OK);
				
			}
			 }catch(Exception e) {
				 throw new ResponseStatusException(HttpStatus.OK,environment.getProperty(e.getMessage()),e);
			 }
			 
			 return response;
			
		}
		@GetMapping(value = "/api/privilegedbuyer/{buyerId}",  produces = MediaType.APPLICATION_JSON_VALUE)
		public boolean privilegedBuyer(@PathVariable Integer buyerId) {
			boolean flag = buyerService.privilegedBuyer(buyerId);
			return flag;
		}
		
			
		
		
		

}
