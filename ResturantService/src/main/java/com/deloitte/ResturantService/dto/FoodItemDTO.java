package com.deloitte.ResturantService.dto;

import com.deloitte.ResturantService.enums.FOOD_AVAILABLITY;
import com.deloitte.ResturantService.enums.FOOD_CATEGORY;

public class FoodItemDTO {

	private Long id;
	
	private String name;

	private FOOD_CATEGORY category;

	private FOOD_AVAILABLITY status;

	private Double price;

	private Double discountedPrice;

	
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

	public FOOD_CATEGORY getCategory() {
		return category;
	}

	public void setCategory(FOOD_CATEGORY category) {
		this.category = category;
	}

	public FOOD_AVAILABLITY getStatus() {
		return status;
	}

	public void setStatus(FOOD_AVAILABLITY status) {
		this.status = status;
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
}
