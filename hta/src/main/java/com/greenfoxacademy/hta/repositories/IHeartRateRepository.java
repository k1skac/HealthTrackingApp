package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.user.HeartRate;
import com.greenfoxacademy.hta.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface IHeartRateRepository extends JpaRepository<HeartRate, Long> {
    @Query("SELECT COUNT(heartRate) FROM HeartRate heartRate WHERE heartRate.user = ?1 AND heartRate.measureTime >= ?2")
    int findHeartRateForNotification(User user, LocalDateTime date);
}
