package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.user.BloodPressure;
import com.greenfoxacademy.hta.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface IBloodPressureRepository extends JpaRepository<BloodPressure, Long> {
    @Query("SELECT COUNT(bloodPressure) FROM BloodPressure bloodPressure WHERE bloodPressure.user = ?1 " +
            "AND bloodPressure.bloodPressureMeasuredAt >= ?2")
    int findBloodPressureForNotification(User user, LocalDateTime date);
}
