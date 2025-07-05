package com.WaterSupplyNotification.WaterSupply.WaterSupply.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.WaterSupplyNotification.WaterSupply.WaterSupply.entity.WaterSupply;
import com.WaterSupplyNotification.WaterSupply.WaterSupply.repository.WaterSupplyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.scheduling.annotation.Scheduled;

import com.WaterSupplyNotification.WaterSupply.WaterSupply.entity.OutboxEvent;
import com.WaterSupplyNotification.WaterSupply.WaterSupply.entity.SupplyStatus;
import com.WaterSupplyNotification.WaterSupply.WaterSupply.entity.WaterSupplyEvent;
import com.WaterSupplyNotification.WaterSupply.WaterSupply.repository.OutboxEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class WaterSupplyService {

    @Autowired
    private WaterSupplyRepository waterSupplyRepository;

   @Autowired
   private OutboxEventRepository outboxEventRepository;

   public void scheduleSupply(WaterSupply supply,String token)
   {
    LocalDateTime supplyTime = supply.getTime();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' hh:mm a");
    String formattedDateTime = supplyTime.format(formatter);

     waterSupplyRepository.save(supply);

     WaterSupplyEvent event= new WaterSupplyEvent();
     event.setLocationId(supply.getLocationId());
     event.setMessage("Water supply schedule at " + formattedDateTime + " for " + supply.getDuration() + "hrs");
     //event.setToken(token);

     ObjectMapper mapper = new ObjectMapper();
        try {
            String eventJson = mapper.writeValueAsString(event);
            System.out.println("Payload being saved: " + eventJson);

            OutboxEvent outboxEvent = new OutboxEvent();
            outboxEvent.setEventType("WaterSupplyScheduled");
            outboxEvent.setPayload(eventJson);
            outboxEvent.setStatus("NEW");

            outboxEventRepository.save(outboxEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize WaterSupplyEvent", e);
        }
   }

    public WaterSupplyService(WaterSupplyRepository waterSupplyRepository)
    {
        this.waterSupplyRepository=waterSupplyRepository;
    }

    public void save(WaterSupply waterSupply)
    {
        waterSupplyRepository.save(waterSupply);
    }

    public WaterSupply getWaterScheduleByLocation(String locationId)
    {
        return waterSupplyRepository.findByLocationId(locationId)
                .orElseThrow(() -> new RuntimeException("No water schedule found for location: " + locationId));
    }

    public void updateWaterSchedule(Long id,WaterSupply updateWaterSupply)
    {
        Optional<WaterSupply> optionalWaterSupply=waterSupplyRepository.findById(id);
        if(optionalWaterSupply.isEmpty())
        {
            throw new RuntimeException("Water supply schedule not found for ID: " + id);
        }

        WaterSupply waterSupply = optionalWaterSupply.get();
        waterSupply.setTime(updateWaterSupply.getTime());
        waterSupply.setDuration(updateWaterSupply.getDuration());
        waterSupply.setStatus(updateWaterSupply.getStatus());
        waterSupply.setCreatedBy(updateWaterSupply.getCreatedBy());

        waterSupplyRepository.save(waterSupply);
    }

    public void deleteWaterSchedule(Long id)
    {
        if (!waterSupplyRepository.existsById(id)) {
            throw new RuntimeException("Water supply schedule not found for ID: " + id);
        }
        waterSupplyRepository.deleteById(id);
    }

    public WaterSupply getWaterSupplyLiveAtLocation(String locationId)
    {
        return waterSupplyRepository.findByLocationIdAndStatus(locationId, "Ongoing")
                .orElseThrow(() -> new RuntimeException("No live water supply found at location: " + locationId));
    }

    @Scheduled(fixedRate = 300000)
    public void updateStatuses() {
    List<WaterSupply> supplies = waterSupplyRepository.findAll();

    LocalDateTime now = LocalDateTime.now();

    for (WaterSupply supply : supplies) {
        if (supply.getStatus() == SupplyStatus.SCHEDULED && now.isAfter(supply.getTime())) {
            supply.setStatus(SupplyStatus.ONGOING);
        } else if (supply.getStatus() == SupplyStatus.ONGOING &&
                   now.isAfter(supply.getTime().plusMinutes(supply.getDuration()))) {
            supply.setStatus(SupplyStatus.COMPLETED);
        }
    }

    waterSupplyRepository.saveAll(supplies);
   }
}
