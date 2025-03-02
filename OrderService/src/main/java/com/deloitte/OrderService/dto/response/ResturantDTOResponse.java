package com.deloitte.OrderService.dto.response;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

import com.deloitte.OrderService.dto.FoodItemsDTO;

public class ResturantDTOResponse {

	private Long id;

	private Instant createdAt;

	private String name;

	private Address address;

	private Double rating;

	private LocalTime openTime;

	private LocalTime closeTime;

	private List<FoodItemsDTO> foodItems;

	private Long userId;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<FoodItemsDTO> getFoodItems() {
		return foodItems;
	}

	public void setFoodItems(List<FoodItemsDTO> foodItems) {
		this.foodItems = foodItems;
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

	@Override
	public String toString() {
		return "ResturantDTOResponse [createdAt=" + createdAt + ", name=" + name + ", address=" + address
				+ ", openTime=" + openTime + ", closeTime=" + closeTime + "]";
	}

}
