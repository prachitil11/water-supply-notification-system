package com.watersupply.Notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.watersupply.Notification.entity.Notification;
import com.watersupply.Notification.service.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController
{
    @Autowired
    public NotificationService notificationService;

    @PostMapping("/test")
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification)
    {
        notificationService.sendNotification(notification);
        return ResponseEntity.ok("Notification sent successfully");
    }

    /*@PostMapping("/byLocationId")
    public ResponseEntity<String> sendNotificationByLocation(@RequestBody Notification notification)
    {
        notificationService.sendNotificationByLocation(notification);
        return ResponseEntity.ok("Notification sent to users at location");
    }*/

    @GetMapping("/test")
    public ResponseEntity<String> testNotification() {
        return ResponseEntity.ok("Notification Service is up!");
    }
}