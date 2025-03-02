package com.deloitte.OrderService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table
@Entity
public class OrderSummary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private Long foodItemIdCode;

	@Column
	private int quantity;
	
	@Column
	private Double originalItemPrice;
	
	@Column
	private Double discountPercantage;
	
	@Column
	private Double discountedItemPrice;
	
	@Column
	private Double totalItemPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private OrderDetails orders;

	
	public Double getTotalItemPrice() {
		return totalItemPrice;
	}

	public void setTotalItemPrice(Double totalItemPrice) {
		this.totalItemPrice = totalItemPrice;
	}

	public Double getOriginalItemPrice() {
		return originalItemPrice;
	}

	public void setOriginalItemPrice(Double originalItemPrice) {
		this.originalItemPrice = originalItemPrice;
	}

	public Double getDiscountPercantage() {
		return discountPercantage;
	}

	public void setDiscountPercantage(Double discountPercantage) {
		this.discountPercantage = discountPercantage;
	}

	public Double getDiscountedItemPrice() {
		return discountedItemPrice;
	}

	public void setDiscountedItemPrice(Double discountedItemPrice) {
		this.discountedItemPrice = discountedItemPrice;
	}

	public OrderDetails getOrders() {
		return orders;
	}

	public void setOrders(OrderDetails orders) {
		this.orders = orders;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getFoodItemIdCode() {
		return foodItemIdCode;
	}

	public void setFoodItemIdCode(Long foodItemIdCode) {
		this.foodItemIdCode = foodItemIdCode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public OrderDetails getOrder() {
		return orders;
	}

	public void setOrder(OrderDetails orders) {
		this.orders = orders;
	}

}
