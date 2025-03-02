package com.deloitte.OrderService.entity;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.deloitte.OrderService.enums.ORDER_STATUS;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table
@Entity
public class OrderDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	@CreationTimestamp
	private Instant createdAt;

	@Column(nullable = false)
	private Long user;

	@Column(nullable = false)
	private Long resturant;

	@Column
	private Double originalCost;

	@Column
	private Double totalCost;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ORDER_STATUS orderStatus;

	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderSummary> orderSummary;

	public Double getOriginalCost() {
		return originalCost;
	}

	public void setOriginalCost(Double originalCost) {
		this.originalCost = originalCost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public void setUser(Long user) {
		this.user = user;
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

	public List<OrderSummary> getOrderSummary() {
		return orderSummary;
	}

	public void setOrderSummary(List<OrderSummary> orderSummary) {
		this.orderSummary = orderSummary;
	}
}
