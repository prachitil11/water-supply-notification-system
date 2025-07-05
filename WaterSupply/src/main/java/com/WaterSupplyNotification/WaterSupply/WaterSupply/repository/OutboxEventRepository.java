package com.WaterSupplyNotification.WaterSupply.WaterSupply.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WaterSupplyNotification.WaterSupply.WaterSupply.entity.OutboxEvent;

@Repository
public interface OutboxEventRepository extends  JpaRepository<OutboxEvent, Long>
{
   List<OutboxEvent> findByStatus(String status);
}