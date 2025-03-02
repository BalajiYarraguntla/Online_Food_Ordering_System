package com.deloitte.OrderService.dto.response;

import java.time.Instant;
import java.util.List;

import com.deloitte.OrderService.dto.OrderSummaryDTO;
import com.deloitte.OrderService.entity.OrderSummary;
import com.deloitte.OrderService.enums.ORDER_STATUS;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderDTOResponse {

	private Long id;
	
	private Instant createdAt;

	private Long user;

	private Long resturant;

	private Double totalCost;
	
	private Double originalCost;

	private ORDER_STATUS orderStatus;

	private List<OrderSummaryDTO> orderSummary;
	
	public Double getOriginalCost() {
		return originalCost;
	}

	public void setOriginalCost(Double originalCost) {
		this.originalCost = originalCost;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUser() {
		return user;
	}

	public Long getResturant() {
		return resturant;
	}

	public void setResturant(Long resturant) {
		this.resturant = resturant;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public ORDER_STATUS getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(ORDER_STATUS orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<OrderSummaryDTO> getOrderSummary() {
		return orderSummary;
	}

	public void setOrderSummary(List<OrderSummaryDTO> orderSummary) {
		this.orderSummary = orderSummary;
	}

}
