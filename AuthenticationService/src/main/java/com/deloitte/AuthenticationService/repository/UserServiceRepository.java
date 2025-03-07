package com.deloitte.AuthenticationService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deloitte.AuthenticationService.entity.User;

@Repository
public interface UserServiceRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String name);
}
