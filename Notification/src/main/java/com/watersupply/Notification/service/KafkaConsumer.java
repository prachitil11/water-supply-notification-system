package com.watersupply.Notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.watersupply.Notification.entity.WaterSupplyEvent;

@Service
public class KafkaConsumer
{
    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics="water-supply-topic", groupId="notification-group")
    public void listen(WaterSupplyEvent event)
    {
        notificationService.sendNotificationByLocation(event.getLocationId(),event.getMessage(),event.getToken());
    }
}