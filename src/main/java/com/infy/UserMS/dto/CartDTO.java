package com.infy.UserMS.dto;

import com.infy.UserMS.entity.CartEntity;

public class CartDTO {
	private Integer buyerId;
	private Integer prodId;
	private Integer quantity;
	
	
	
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getProdId() {
		return prodId;
	}
	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	//Converts Entity to DTO
	public static CartDTO valueOf(CartEntity cart) {
		CartDTO cartDTO = new CartDTO();
		cartDTO.setBuyerId(cart.getBuyerId());
		cartDTO.setProdId(cart.getProdId());
		cartDTO.setQuantity(cart.getQuantity());
		return cartDTO;
	}
	// Converts DTO into Entity
	public CartEntity createEntity() {
		CartEntity cart = new CartEntity();
		cart.setBuyerId(this.getBuyerId());
		cart.setProdId(this.getProdId());
		cart.setQuantity(this.getQuantity());
		return cart;
	}
	
	

}
