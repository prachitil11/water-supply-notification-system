package com.WaterSupplyNotification.WaterSupply.WaterSupply.entity;
import lombok.Data;

@Data
public class WaterSupplyEvent
{
    private String locationId;
    private String message;
    private String token;
}