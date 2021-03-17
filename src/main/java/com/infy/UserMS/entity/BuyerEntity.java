package com.infy.UserMS.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="buyer")
public class BuyerEntity {
	@Id
	@Column(name="BUYERID",nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int buyerId;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String phonenumber;
	@Column(nullable = false)
	private String password;
	@Column(name="ISPRIVILEGED",nullable = false, length = 1)
	private int isPrivileged;
	@Column(name="REWARDPOINTS",nullable = false)
	private int rewardPoints;
	@Column(name="ISACTIVE",nullable = false, length = 1)
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
	
	

}
