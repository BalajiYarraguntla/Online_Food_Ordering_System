package com.deloitte.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deloitte.notificationservice.entity.Notification;

import java.util.List;
 
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(String userId);
}
