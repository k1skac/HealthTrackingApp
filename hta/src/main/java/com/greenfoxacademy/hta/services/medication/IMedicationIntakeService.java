package com.greenfoxacademy.hta.services.medication;

import com.greenfoxacademy.hta.models.User;

import java.time.LocalDateTime;
import java.util.List;

public interface IMedicationIntakeService {
    int getMedicationIntakesForNotification(User user, LocalDateTime today, String medicationName);
    List<String> notificationMessages(User user, LocalDateTime today);
}