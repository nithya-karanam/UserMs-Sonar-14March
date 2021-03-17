package com.infy.UserMS.dto;

import com.infy.UserMS.entity.BuyerEntity;

public class BuyerDTO {
	private int buyerId;
	private String name;
	private String email;
	private String phonenumber;
	private String password;
	private int isPrivileged;
	private int rewardPoints;
	private int isActive;
	
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
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
	public int getIsPrivileged() {
		return isPrivileged;
	}
	public void setIsPrivileged(int isPrivileged) {
		this.isPrivileged = isPrivileged;
	}
	public int getRewardPoints() {
		return rewardPoints;
	}
	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	//Converts entity to DTO
	public static BuyerDTO valueOf(BuyerEntity buyer) {
		BuyerDTO buyerDTO = new BuyerDTO();
		buyerDTO.setBuyerId(buyer.getBuyerId());
		buyerDTO.setName(buyer.getName());
		buyerDTO.setEmail(buyer.getEmail());
		buyerDTO.setPhonenumber(buyer.getPhonenumber());
		buyerDTO.setPassword(buyer.getPassword());
		buyerDTO.setIsActive(buyer.getIsActive());
		buyerDTO.setIsPrivileged(buyer.getIsPrivileged());
		buyerDTO.setRewardPoints(buyer.getRewardPoints());
		return buyerDTO;
	}
	// Converts DTO into Entity
	public BuyerEntity createEntity() {
		BuyerEntity buyer = new BuyerEntity();
		buyer.setBuyerId(this.getBuyerId());
		buyer.setName(this.getName());
		buyer.setEmail(this.getEmail());
		buyer.setPhonenumber(this.getPhonenumber());
		buyer.setPassword(this.getPassword());
		buyer.setIsActive(this.getIsActive());
		buyer.setIsPrivileged(this.getIsPrivileged());
		buyer.setRewardPoints(this.getRewardPoints());
		return buyer;
	}
	
	

}
