package com.infy.UserMS.dto;

import com.infy.UserMS.entity.WishListEntity;

public class WishListDTO {
	private int buyerId;
	private int prodId;
	
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public int getProdId() {
		return prodId;
	}
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}
	
	//Converts Entity to DTO
	public static WishListDTO valueOf(WishListEntity wishList) {
		WishListDTO wishListDTO = new WishListDTO();
		wishListDTO.setBuyerId(wishList.getBuyerId());
		wishListDTO.setProdId(wishList.getProdId());
		return wishListDTO;
	}
	// Converts DTO into Entity
	public WishListEntity createEntity() {
		WishListEntity wishList = new WishListEntity();
		wishList.setBuyerId(this.getBuyerId());
		wishList.setProdId(this.getProdId());
		return wishList;
	}

}
