package com.deloitte.OrderService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deloitte.OrderService.entity.OrderSummary;

@Repository
public interface OrderSummaryRepository extends JpaRepository<OrderSummary, Long>{

}
