package com.WaterSupplyNotification.WaterSupply.WaterSupply.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.WaterSupplyNotification.WaterSupply.WaterSupply.entity.OutboxEvent;
import com.WaterSupplyNotification.WaterSupply.WaterSupply.entity.WaterSupplyEvent;
import com.WaterSupplyNotification.WaterSupply.WaterSupply.repository.OutboxEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OutboxEventPublisher
{
   @Autowired
   private KafkaTemplate<String, WaterSupplyEvent> kafkaTemplate;

   @Autowired
   private OutboxEventRepository outboxEventRepository;

   @Autowired
   private ObjectMapper objectMapper;

   @Scheduled(fixedDelay=100000)
   public void publishNewEvents()
   {
      List<OutboxEvent> newEvents=outboxEventRepository.findByStatus("NEW");
      for(OutboxEvent outboxEvent:newEvents)
      {
        try {
                String payload = outboxEvent.getPayload();
                System.out.println("Payload to be published: " + payload);
                WaterSupplyEvent event = objectMapper.readValue(payload, WaterSupplyEvent.class);
                kafkaTemplate.send("water-supply-topic", event);
                outboxEvent.setStatus("SENT");
            } catch (Exception e) {
                outboxEvent.setStatus("FAILED");
                e.printStackTrace();
            }
            outboxEventRepository.save(outboxEvent);
      }
   }
}