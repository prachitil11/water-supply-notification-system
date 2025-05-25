package com.watersupply.Notification.entity;

import lombok.Data;

@Data
public class WaterSupplyEvent {
    private String locationId;
    private String message;
    private String token;
}