package com.WaterSupplyNotification.User.repository;

import java.util.List;

import com.WaterSupplyNotification.User.entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    public Users findByEmail(String email);
    public List<Users> findByLocationId(String locationId);
}
