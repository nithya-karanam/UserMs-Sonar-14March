package com.infy.UserMS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.UserMS.dto.LoginDTO;
import com.infy.UserMS.dto.SellerDTO;
import com.infy.UserMS.entity.SellerEntity;
import com.infy.UserMS.repository.SellerRepository;
import com.infy.validator.Validator;

@Service
public class SellerService {
	
	@Autowired
	SellerRepository sellerRepo;
	
	public void createSeller(SellerDTO sellerDTO) throws Exception{
		Validator.validateSeller(sellerDTO);
		Optional<SellerEntity> optSellerPhone = sellerRepo.findByPhonenumber(sellerDTO.getPhonenumber());
		Optional<SellerEntity> optSellerEmail = sellerRepo.findByEmail(sellerDTO.getEmail());
		if(optSellerPhone.isPresent()) {
			SellerEntity seller = optSellerPhone.get();
			if(seller.getIsActive() == 0) {
				throw new Exception("ACCOUNT_REGISTERED");
			}
			throw new Exception("USER_PHONE_EXISTS");
		}
		if(optSellerEmail.isPresent()) {
			throw new Exception("USER_EMAIL_EXISTS");
		}
		SellerEntity Seller = sellerDTO.createEntity();
		Seller.setIsActive(1);
		sellerRepo.save(Seller);
	}
	
	public boolean login(LoginDTO loginDTO) throws Exception{
		Optional<SellerEntity> optSeller = sellerRepo.findByEmail(loginDTO.getEmail());
		if (optSeller.isPresent()) {
			SellerEntity seller = optSeller.get();
			if (seller.getPassword().equals(loginDTO.getPassword())) {
				if(seller.getIsActive() != 1) {
					throw new Exception("ACCOUNT_DEACTIVATED");
				}
				return true;
			}
		}
		return false;
	}
	public List<SellerDTO> sellerListAll() {
		List<SellerDTO> sellerDTOList = new ArrayList<>();
		List<SellerEntity> sellerList = sellerRepo.findAll();
	    for(SellerEntity seller:sellerList) {
			SellerDTO sellerDTO = SellerDTO.valueOf(seller);
			sellerDTOList.add(sellerDTO);
			
		}
		return sellerDTOList;
		
	}
	
	public boolean deleteSeller(String email) {
		Optional<SellerEntity> optSeller = sellerRepo.findByEmail(email);
		if(optSeller.isPresent()) {
			SellerEntity Seller = optSeller.get();
			sellerRepo.delete(Seller);
			return true;
		}
		return false;
	}
	
	public Integer deactivateSeller(String email) {
		Optional<SellerEntity> optSeller = sellerRepo.findByEmail(email);
		Integer Id = null;
		if(optSeller.isPresent()) {
			SellerEntity Seller = optSeller.get();
			if(Seller.getIsActive() == 1) {
				Seller.setIsActive(0);
				sellerRepo.save(Seller);
				Id = Seller.getSellerId();
				return Id;
			}
		}
		return null;
	}
	

}
