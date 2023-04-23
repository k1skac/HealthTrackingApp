package com.greenfoxacademy.hta.services.weight;

import com.greenfoxacademy.hta.models.User;

import java.time.LocalDateTime;

public interface IWeightService {
    int getWeightForNotification(User user, LocalDateTime date);
    String notificationMessage(User user, LocalDateTime today);
}