package com.deloitte.NotificationService.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.deloitte.NotificationService.entity.User;
import com.deloitte.NotificationService.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    public User saveUser(User user) {
        System.out.println("Received user: " + user); // Debugging
        if (user.getEmail() == null || user.getName() == null) {
            System.out.println("ERROR: Missing email or name!");
            throw new RuntimeException("User email or name is null!");
        }

        User savedUser = userRepository.save(user);
        sendEmailNotification(user.getEmail(), user.getName()); // Check if email sending is causing the error
        return savedUser;
    }

    private void sendEmailNotification(String email, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Order status  ");
        message.setText("Hello " + name + ",\n\nYour order successfully completed!\n\nBest Regards,\nYour Company");

        mailSender.send(message);
    }
}
