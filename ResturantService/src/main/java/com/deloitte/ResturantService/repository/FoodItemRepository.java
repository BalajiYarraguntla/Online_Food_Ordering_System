package com.deloitte.ResturantService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deloitte.ResturantService.entity.FoodItem;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long>{

}
