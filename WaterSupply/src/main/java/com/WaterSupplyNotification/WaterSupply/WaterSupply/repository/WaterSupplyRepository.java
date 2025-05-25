package com.WaterSupplyNotification.WaterSupply.WaterSupply.repository;

import com.WaterSupplyNotification.WaterSupply.WaterSupply.entity.WaterSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WaterSupplyRepository extends JpaRepository<WaterSupply,Long> {
    public Optional<WaterSupply> findByLocationId(String locationId);
    public Optional<WaterSupply> findByLocationIdAndStatus(String locationId,String status);
}
