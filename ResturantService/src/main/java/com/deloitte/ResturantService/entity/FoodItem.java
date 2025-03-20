package com.deloitte.ResturantService.entity;

import com.deloitte.ResturantService.enums.FOOD_AVAILABLITY;
import com.deloitte.ResturantService.enums.FOOD_CATEGORY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class FoodItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private FOOD_CATEGORY category;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private FOOD_AVAILABLITY status;

	@Column(nullable = false)
	private Double price;

	@Column(nullable = false)
	private Double discountedPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resturant_id")
	private Resturant resturant;

	
	public Resturant getResturant() {
		return resturant;
	}

	public void setResturant(Resturant resturant) {
		this.resturant = resturant;
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

	@Override
	public String toString() {
		return "FoodItem [id=" + id + ", name=" + name + ", category=" + category + ", status=" + status + ", price="
				+ price + ", discountedPrice=" + discountedPrice + ", resturant=" + resturant + "]";
	}
}
