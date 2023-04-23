package com.greenfoxacademy.hta.services.weight;

import com.greenfoxacademy.hta.models.*;
import com.greenfoxacademy.hta.models.notifications.NotificationMessage;
import com.greenfoxacademy.hta.repositories.IWeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WeightService implements IWeightService {
    private final IWeightRepository weightRepository;

    @Autowired
    public WeightService(IWeightRepository weightRepository) {
        this.weightRepository = weightRepository;
    }

    @Override
    public int getWeightForNotification(User user, LocalDateTime date) {
        return weightRepository.findWeightForNotification(user, date);
    }

    @Override
    public String notificationMessage(User user, LocalDateTime today) {
        if (user.getNotification().getIsWeightAlertOn()) {
            if (getWeightForNotification(user, today) < user.getNotification().getWeightMeasurementFrequency()) {
                return NotificationMessage.WEIGHT.getMessage();
            }
        }
        return null;
    }
}