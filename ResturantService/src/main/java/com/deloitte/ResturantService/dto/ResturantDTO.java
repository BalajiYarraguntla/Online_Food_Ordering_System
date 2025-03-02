package com.deloitte.ResturantService.dto;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

import com.deloitte.ResturantService.entity.Address;
import com.deloitte.ResturantService.enums.RESTURANT_VERIFICATION_STATUS;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class ResturantDTO {

	private Instant createdAt;

	@NotNull(message = "Resturant Name cannot be Empty")
	private String name;

	@Valid
	private Address address;

	private RESTURANT_VERIFICATION_STATUS isVerified;

	@NotNull(message = "Resturant openTime cannot be Empty")
	private LocalTime openTime;

	@NotNull(message = "Resturant closeTime cannot be Empty")
	private LocalTime closeTime;
	private List<FoodItemDTO> foodItems;
	
	private Double rating;

	private Integer orderCapacity;
	
	
	public Integer getOrderCapacity() {
		return orderCapacity;
	}

	public void setOrderCapacity(Integer orderCapacity) {
		this.orderCapacity = orderCapacity;
	}
	
	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public RESTURANT_VERIFICATION_STATUS getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(RESTURANT_VERIFICATION_STATUS isVerified) {
		this.isVerified = isVerified;
	}

	public LocalTime getOpenTime() {
		return openTime;
	}

	public void setOpenTime(LocalTime openTime) {
		this.openTime = openTime;
	}

	public LocalTime getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(LocalTime closeTime) {
		this.closeTime = closeTime;
	}

	public List<FoodItemDTO> getFoodItems() {
		return foodItems;
	}

	public void setFoodItems(List<FoodItemDTO> foodItems) {
		this.foodItems = foodItems;
	}

	@Override
	public String toString() {
		return "ResturantDTO [createdAt=" + createdAt + ", name=" + name + ", address=" + address + ", isVerified="
				+ isVerified + ", openTime=" + openTime + ", closeTime=" + closeTime + ", foodItems=" + foodItems + "]";
	}

}
