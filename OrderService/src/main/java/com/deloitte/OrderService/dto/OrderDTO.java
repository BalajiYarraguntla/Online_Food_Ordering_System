package com.deloitte.OrderService.dto;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.deloitte.OrderService.entity.OrderSummary;
import com.deloitte.OrderService.enums.ORDER_STATUS;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class OrderDTO {

	private Instant createdAt;

	private Long user;

	private Long resturant;

	private Double originalCost;

	private Double totalCost;

	private ORDER_STATUS orderStatus;

	private List<OrderSummary> orderSummary;

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

	public ORDER_STATUS getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(ORDER_STATUS orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<OrderSummary> getOrderSummary() {
		return orderSummary;
	}

	public void setOrderSummary(List<OrderSummary> orderSummary) {
		this.orderSummary = orderSummary;
	}

	@Override
	public String toString() {
		return "OrderDTO [createdAt=" + createdAt + ", user=" + user + ", resturant=" + resturant
				+ ", originalCost=" + originalCost + ", totalCost=" + totalCost + ", orderStatus=" + orderStatus
				+ ", orderSummary=" + orderSummary + "]";
	}
}
