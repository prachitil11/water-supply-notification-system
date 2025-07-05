package com.watersupply.Notification.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.watersupply.Notification.entity.Notification;
import com.watersupply.Notification.entity.Users;
import com.watersupply.Notification.repository.NotificationRepository;

import feign.Feign;

@Service
public class NotificationService{

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private Feign.Builder feignBuilder;

    @Value("${internal.service.token}")
    private String internalToken;

    public void sendNotification(Notification notification)
    {
        Users user = userService.getUserById(notification.getUserId());
        sendEmailService.sendEmail(user.getEmail(), notification.getMessage());
        notification.setTimestamp(LocalDateTime.now());
        notification.setStatus("SENT");
        notificationRepository.save(notification);
    }

    /*public void sendNotificationByLocation(Notification notification) {
        List<Users> users=userService.getUserByLocation(notification.getLocationId());
        for(Users user:users)
        {
            Notification newNotification=new Notification();
            newNotification.setUserId(user.getId());
            newNotification.setLocationId(notification.getLocationId());
            newNotification.setNotificationType(notification.getNotificationType());
            newNotification.setMessage(notification.getMessage());
            newNotification.setStatus("SENT");
            newNotification.setTimestamp(LocalDateTime.now());

            // Save and send email
            notificationRepository.save(newNotification);
            sendEmailService.sendEmail(user.getEmail(), notification.getMessage());

        }
    }*/

    public void sendNotificationByLocation(String locationId, String message) {
    List<Users> users = userService.getUserByLocation(locationId, "Bearer " + internalToken);
    for (Users user : users) {
        Notification notification = new Notification();
        notification.setUserId(user.getId());
        notification.setLocationId(locationId);
        notification.setMessage(message);
        notification.setNotificationType("EMAIL");
        notification.setStatus("SENT");
        notification.setTimestamp(LocalDateTime.now());

        notificationRepository.save(notification);
        sendEmailService.sendEmail(user.getEmail(), message);
    }
}

   
}