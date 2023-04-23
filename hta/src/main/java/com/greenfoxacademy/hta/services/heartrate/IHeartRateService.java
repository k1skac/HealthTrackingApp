package com.greenfoxacademy.hta.services.heartrate;

import com.greenfoxacademy.hta.models.User;

import java.time.LocalDateTime;

public interface IHeartRateService {
    int getHeartRateForNotification(User user, LocalDateTime date);
    String notificationMessage(User user, LocalDateTime today);
}