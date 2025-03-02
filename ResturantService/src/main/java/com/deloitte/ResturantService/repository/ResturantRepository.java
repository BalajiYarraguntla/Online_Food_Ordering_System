package com.deloitte.ResturantService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deloitte.ResturantService.entity.Resturant;

@Repository
public interface ResturantRepository extends JpaRepository<Resturant, Long> {

	Optional<Resturant> findByUserId(long id);
}
