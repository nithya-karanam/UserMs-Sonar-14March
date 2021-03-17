package com.infy.UserMS.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="wishlist")
@IdClass(value=WishListEntityUsingIdClass.class)
@Data
@NoArgsConstructor
public class WishListEntity {
	@Id
	@Column(name="BUYERID",nullable = false)
	private int buyerId;
	@Id
	@Column(name="PRODID",nullable = false)
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
	

}
