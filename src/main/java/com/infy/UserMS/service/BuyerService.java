package com.infy.UserMS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.UserMS.dto.BuyerDTO;
import com.infy.UserMS.dto.LoginDTO;
import com.infy.UserMS.entity.BuyerEntity;
import com.infy.UserMS.entity.CartEntity;
import com.infy.UserMS.repository.BuyerRepository;
import com.infy.UserMS.repository.CartRepository;
import com.infy.validator.Validator;

@Service
public class BuyerService {
	
	@Autowired
	BuyerRepository buyerRepo;
	
	@Autowired
	CartRepository cartRepo;
	
	public void createBuyer(BuyerDTO buyerDTO) throws Exception{
		Validator.validateBuyer(buyerDTO);
		Optional<BuyerEntity> optBuyerPhone = buyerRepo.findByPhonenumber(buyerDTO.getPhonenumber());
		Optional<BuyerEntity> optBuyerEmail = buyerRepo.findByEmail(buyerDTO.getEmail());
		if(optBuyerPhone.isPresent()) {
			BuyerEntity buyer = optBuyerPhone.get();
			if(buyer.getIsActive() == 0) {
				throw new Exception("ACCOUNT_REGISTERED");
			}
			throw new Exception("USER_PHONE_EXISTS");
		}
		if(optBuyerEmail.isPresent()) {
			throw new Exception("USER_EMAIL_EXISTS");
		}
		BuyerEntity Buyer = buyerDTO.createEntity();
		if(buyerDTO.getRewardPoints()>10000) {
			Buyer.setIsPrivileged(1);
		
		}
		Buyer.setIsActive(1);
		buyerRepo.save(Buyer);
	}
	
	public boolean login(LoginDTO loginDTO) throws Exception{
		Optional<BuyerEntity> optBuyer = buyerRepo.findByEmail(loginDTO.getEmail());
		if (optBuyer.isPresent()) {
			BuyerEntity buyer = optBuyer.get();
			if (buyer.getPassword().equals(loginDTO.getPassword())) {
				if(buyer.getIsActive() != 1) {
					throw new Exception("ACCOUNT_DEACTIVATED");
				}
				return true;
			}
		}
		return false;
	}
	
	public List<BuyerDTO> buyerListAll() {
		List<BuyerDTO> buyerDTOList = new ArrayList<>();
		List<BuyerEntity> buyerList = buyerRepo.findAll();
	    for(BuyerEntity buyer:buyerList) {
			BuyerDTO buyerDTO = BuyerDTO.valueOf(buyer);
			buyerDTOList.add(buyerDTO);
			
		}
		return buyerDTOList;
		
	}
	
	public boolean deleteBuyer(String email) {
		Optional<BuyerEntity> optBuyer = buyerRepo.findByEmail(email);
		if(optBuyer.isPresent()) {
			BuyerEntity Buyer = optBuyer.get();
			buyerRepo.delete(Buyer);
			return true;
		}
		return false;
	}
	
	public boolean deactivateBuyer(String email) {
		Optional<BuyerEntity> optBuyer = buyerRepo.findByEmail(email);
		if(optBuyer.isPresent()) {
			BuyerEntity Buyer = optBuyer.get();
			if(Buyer.getIsActive() == 1) {
				Buyer.setIsActive(0);
				buyerRepo.save(Buyer);
				return true;
			}
		}
		return false;
	}
	//Product Ids in cartList
	public List<Integer> getProductIdsFromCart(int buyerId){
		List<CartEntity> cartEntityList = cartRepo.findByBuyerId(buyerId);
		List<Integer> prodIdList = new ArrayList<Integer>();
		for(CartEntity cart: cartEntityList) {
			prodIdList.add(cart.getProdId());
		}
		return prodIdList;
	}
	//Modifies amount based on rewards points
	public double getModifiedAmount(double amount,int buyerId) {
		int rewardPoints = (int) amount/100;
		System.out.println(rewardPoints);
		Optional<BuyerEntity> optBuyer = buyerRepo.findById(buyerId);
		System.out.println(optBuyer.isPresent());
		if(optBuyer.isPresent()) {
			BuyerEntity buyer = optBuyer.get();
			rewardPoints = rewardPoints + buyer.getRewardPoints();
			System.out.println(rewardPoints);
			int quotient = rewardPoints/4;
			int remainder = rewardPoints%4;
			amount = amount - quotient*1;
			if(amount<0) {
				quotient = (int) amount;
				int rewardPointsNew= 4*quotient;
				rewardPoints = rewardPoints - rewardPointsNew;
				remainder = remainder + rewardPoints;
				return 0;
			}
			buyer.setRewardPoints(remainder);
			System.out.println(remainder);
			buyerRepo.save(buyer);
			return amount;
		}
		return -1.0;
	}
	
	public boolean privilegedBuyer(int buyerId) {
		Optional<BuyerEntity> optBuyer = buyerRepo.findById(buyerId);
		if(optBuyer.isPresent()) {
			BuyerEntity buyer = optBuyer.get();
			if(buyer.getRewardPoints()>10000) {
				buyer.setIsPrivileged(1);
				buyerRepo.save(buyer);
				return true;
			}
		}
		return false;
	}
	
	

}
