package com.watersupply.Notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.watersupply.Notification.entity.WaterSupplyEvent;

@Service
public class KafkaConsumer
{

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics="water-supply-topic", groupId="notification-group")
    public void listen(WaterSupplyEvent event)
    {
        notificationService.sendNotificationByLocation(event.getLocationId(),event.getMessage());
    }

    @KafkaListener(topics = "water-supply-topic.DLQ", groupId = "dlq-group")
    public void handleDeadLetter(String message) {
         logger.error("Message moved to DLQ: " + message);
    // Optionally alert via email or log monitoring
    }

}