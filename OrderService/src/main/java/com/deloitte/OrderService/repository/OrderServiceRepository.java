package com.deloitte.OrderService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deloitte.OrderService.entity.OrderDetails;

@Repository
public interface OrderServiceRepository extends JpaRepository<OrderDetails, Integer>{

	Optional<List<OrderDetails>> findAllByResturant(Long id);
	
	Optional<List<OrderDetails>> findAllByUser(Long id);
}
