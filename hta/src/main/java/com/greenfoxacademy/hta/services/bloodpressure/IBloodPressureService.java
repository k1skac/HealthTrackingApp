package com.greenfoxacademy.hta.services.bloodpressure;

import com.greenfoxacademy.hta.models.user.BloodPressure;
import com.greenfoxacademy.hta.models.user.User;
import java.time.LocalDateTime;

public interface IBloodPressureService {
    int getBloodPressureForNotification(User user, LocalDateTime date);
    String notificationMessage(User user, LocalDateTime today);
    void save(BloodPressure bloodPressure, String email);
}