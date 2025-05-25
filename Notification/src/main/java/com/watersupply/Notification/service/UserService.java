package com.watersupply.Notification.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.watersupply.Notification.entity.Users;

@FeignClient(name = "User")
public interface UserService
{
    @GetMapping("/users/location/{locationId}")
     List<Users> getUserByLocation(@PathVariable String locationId, @RequestHeader("Authorization") String token);

     @GetMapping("/users/{id}")
     Users getUserById(@PathVariable("id") Long id);
}