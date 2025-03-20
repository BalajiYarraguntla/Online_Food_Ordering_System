package com.deloitte.ResturantService.entity;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import com.deloitte.ResturantService.enums.RESTURANT_VERIFICATION_STATUS;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Resturant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column
	@CreationTimestamp
	private Instant createdAt;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RESTURANT_VERIFICATION_STATUS isVerified;

	@Column(nullable = false)
	private LocalTime openTime;

	@Column(nullable = false)
	private Double rating;
	
	@Column(nullable = false)
	private LocalTime closeTime;

	@OneToMany( mappedBy = "resturant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<FoodItem> foodItems;
	
	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
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

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
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

	public List<FoodItem> getFoodItems() {
		return foodItems;
	}

	public void setFoodItems(List<FoodItem> foodItems) {
		this.foodItems = foodItems;
	}

	@Override
	public String toString() {
		return "Resturant [id=" + id + ", name=" + name + ", createdAt=" + createdAt + ", address=" + address
				+ ", isVerified=" + isVerified + ", openTime=" + openTime + ", closeTime=" + closeTime + ", foodItems="
				+ foodItems + ", userId=" + userId + "]";
	}
	
}
