package com.watersupply.Notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.watersupply.Notification.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}