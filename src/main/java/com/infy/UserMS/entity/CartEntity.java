package com.infy.UserMS.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity
@Table(name="cart")
@IdClass(value=CartEntityUsingIdClass.class)
public class CartEntity {
	@Id
	@Column(name="BUYERID",nullable = false)
	private int buyerId;
	@Id
	@Column(name="PRODID",nullable = false)
	private int prodId;
	@Column(nullable = false)
	private int quantity;
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

}
