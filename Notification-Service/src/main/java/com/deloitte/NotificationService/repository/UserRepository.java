package com.deloitte.NotificationService.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.deloitte.NotificationService.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
