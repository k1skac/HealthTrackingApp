package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.BloodPressure;
import com.greenfoxacademy.hta.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface IBloodPressureRepository extends JpaRepository<BloodPressure, Long> {
    @Query("SELECT COUNT(bloodPressure) FROM BloodPressure bloodPressure WHERE bloodPressure.user = ?1 " +
            "AND bloodPressure.measureTime >= ?2")
    int findBloodPressureForNotification(User user, LocalDateTime date);
}
