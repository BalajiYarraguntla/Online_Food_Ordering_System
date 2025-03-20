package com.deloitte.notificationservice.controller;


import com.deloitte.notificationservice.entity.Notification;
import com.deloitte.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
 
    private final NotificationService notificationService = new NotificationService();
 
    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable String userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }
 
    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestParam String userId, 
                                                   @RequestParam String message,
                                                   @RequestParam String type) {
        notificationService.sendNotification(userId, message, type);
        return ResponseEntity.ok("Notification sent successfully");
    }
}
 
