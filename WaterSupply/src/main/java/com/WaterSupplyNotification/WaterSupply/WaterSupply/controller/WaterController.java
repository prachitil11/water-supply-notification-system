package com.WaterSupplyNotification.WaterSupply.WaterSupply.controller;

import com.WaterSupplyNotification.WaterSupply.WaterSupply.entity.WaterSupply;
import com.WaterSupplyNotification.WaterSupply.WaterSupply.service.WaterSupplyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.ReactiveOffsetScrollPositionHandlerMethodArgumentResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/water-supply")
public class WaterController {

    @Autowired
    private WaterSupplyService waterSupplyService;

    public WaterController(WaterSupplyService waterSupplyService)
    {
        this.waterSupplyService=waterSupplyService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/schedule")
    public ResponseEntity<String> scheduleWaterSupply(@RequestBody WaterSupply waterSupply,HttpServletRequest request)
    {
        String token=request.getHeader("Authorization");
        waterSupplyService.scheduleSupply(waterSupply,token);
        return ResponseEntity.ok("WaterSupply details saved successfully");
    }

    @GetMapping("upcoming/{locationId}")
    public ResponseEntity<WaterSupply> getUpcomingWaterScheduleByLocation(@PathVariable String locationId)
    {
        WaterSupply waterSupply=waterSupplyService.getWaterScheduleByLocation(locationId);
        return ResponseEntity.ok(waterSupply);
    }

    @GetMapping("/live/{locationId}")
    public ResponseEntity<WaterSupply> getWaterSupplyLiveAtLocation(@PathVariable String locationId)
    {
        WaterSupply waterSupply=waterSupplyService.getWaterSupplyLiveAtLocation(locationId);
        return ResponseEntity.ok(waterSupply);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWaterSchedule(@PathVariable long id,@RequestBody WaterSupply updateWaterSupply)
    {
        waterSupplyService.updateWaterSchedule(id,updateWaterSupply);
        return ResponseEntity.ok("Water supply schedule updated successfully.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWaterSchedule(@PathVariable long id)
    {
        waterSupplyService.deleteWaterSchedule(id);
        return ResponseEntity.ok("Water supply schedule deleted successfully.");
    }
}
