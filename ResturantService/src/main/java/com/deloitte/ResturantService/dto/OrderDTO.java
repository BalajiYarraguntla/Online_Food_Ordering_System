package com.deloitte.ResturantService.dto;


import java.time.Instant;
import java.util.List;


public class OrderDTO {

	private Instant createdAt;

	private Long user;

	private Long resturant;

	private Double originalCost;

	private Double totalCost;

	private String orderStatus;

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Long getResturant() {
		return resturant;
	}

	public void setResturant(Long resturant) {
		this.resturant = resturant;
	}

	public Double getOriginalCost() {
		return originalCost;
	}

	public void setOriginalCost(Double originalCost) {
		this.originalCost = originalCost;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

}
