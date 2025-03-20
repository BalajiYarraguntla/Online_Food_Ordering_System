package com.deloitte.notificationservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.deloitte.notificationservice.entity.Notification;
import com.deloitte.notificationservice.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class NotificationService {
 
    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;
 
    public void sendNotification(String userId, String message, String type) {
        Notification notification = Notification.builder()
                .userId(userId)
                .message(message)
                .type(type)
                .createdAt(LocalDateTime.now())
                .build();
        notificationRepository.save(notification);
 
        switch (type.toUpperCase()) {
            case "EMAIL":
                sendEmail(userId, "Notification", message);
                break;
            case "SMS":
                sendSms(userId, message);
                break;
            case "PUSH":
                sendPushNotification(userId, message);
                break;
            default:
                System.out.println("Unsupported notification type: " + type);
        }
    }
 
    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        System.out.println("Email sent to " + to);
    }
 
    private void sendSms(String phoneNumber, String message) {
        System.out.println("SMS sent to " + phoneNumber + ": " + message);
    }
 
    private void sendPushNotification(String userId, String message) {
        System.out.println("Push Notification sent to user: " + userId + " - " + message);
    }
 
    public List<Notification> getUserNotifications(String userId) {
        return notificationRepository.findByUserId(userId);
    }
}
 