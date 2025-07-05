package com.WaterSupplyNotification.WaterSupply.WaterSupply.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaterSupplyEvent
{
    private String locationId;
    private String message;
    //private String token;
}