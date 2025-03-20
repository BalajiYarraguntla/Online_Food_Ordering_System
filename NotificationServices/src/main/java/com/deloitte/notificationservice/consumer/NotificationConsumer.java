package com.deloitte.notificationservice.consumer;

import org.springframework.stereotype.Service;

import com.deloitte.notificationservice.service.NotificationService;
 
@Service
public class NotificationConsumer {
 
    private final NotificationService notificationService;
 
    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
 
    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void consumeOrderEvent(String message) {
        System.out.println("Received Order Event: " + message);
        notificationService.sendNotification("user@example.com", message, "EMAIL");
    }
}
