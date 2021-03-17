package com.infy.UserMS.dto;

import com.infy.UserMS.entity.SellerEntity;

public class SellerDTO {
	private int sellerId;
	private String name;
	private String email;
	private String phonenumber;
	private String password;
	private int isActive;
	
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	//Converts entity to DTO
		public static SellerDTO valueOf(SellerEntity seller) {
			SellerDTO sellerDTO = new SellerDTO();
			sellerDTO.setSellerId(seller.getSellerId());
			sellerDTO.setName(seller.getName());
			sellerDTO.setEmail(seller.getEmail());
			sellerDTO.setPhonenumber(seller.getPhonenumber());
			sellerDTO.setPassword(seller.getPassword());
			sellerDTO.setIsActive(seller.getIsActive());
			return sellerDTO;
		}
		// Converts DTO into Entity
		public SellerEntity createEntity() {
			SellerEntity seller = new SellerEntity();
			seller.setSellerId(this.getSellerId());
			seller.setName(this.getName());
			seller.setEmail(this.getEmail());
			seller.setPhonenumber(this.getPhonenumber());
			seller.setPassword(this.getPassword());
			seller.setIsActive(this.getIsActive());
			return seller;
		}
	
	

}
