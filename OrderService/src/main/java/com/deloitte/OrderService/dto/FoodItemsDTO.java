package com.deloitte.OrderService.dto;

public class FoodItemsDTO {

	private Long id;
	
	private String name;
	
	private String category;
	
	private String status;
	
	private Double price;
	
	private Double discountedPrice;

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(Double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	@Override
	public String toString() {
		return "FoodItemsDTO [id=" + id + ", name=" + name + ", category=" + category + ", price=" + price
				+ ", discountedPrice=" + discountedPrice + "]";
	}
	
}
