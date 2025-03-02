package com.deloitte.OrderService.dto;

public class OrderSummaryDTO {

	private Long foodItemIdCode;

	private int quantity;

	private Double originalItemPrice;
	
	private String discountPercantage;
	
	private Double discountedItemPrice;
	
	private Double totalItemPrice;
	
	
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

	public String getDiscountPercantage() {
		return discountPercantage;
	}

	public void setDiscountPercantage(String discountPercantage) {
		this.discountPercantage = discountPercantage + "%";
	}

	public Double getDiscountedItemPrice() {
		return discountedItemPrice;
	}

	public void setDiscountedItemPrice(Double discountedItemPrice) {
		this.discountedItemPrice = discountedItemPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getFoodItemIdCode() {
		return foodItemIdCode;
	}

	public void setFoodItemIdCode(Long foodItemIdCode) {
		this.foodItemIdCode = foodItemIdCode;
	}

	@Override
	public String toString() {
		return "OrderSummaryDTO [quantity=" + quantity + ", foodItemIdCode=" + foodItemIdCode + "]";
	}
	
	
}
