package com.greenfoxacademy.hta.services.heartrate;

import com.greenfoxacademy.hta.models.notifications.NotificationMessage;
import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.repositories.IHeartRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HeartRateService implements IHeartRateService {
    private final IHeartRateRepository heartRateRepository;

    @Autowired
    public HeartRateService(IHeartRateRepository heartRateRepository) {
        this.heartRateRepository = heartRateRepository;
    }

    @Override
    public int getHeartRateForNotification(User user, LocalDateTime date) {
        return heartRateRepository.findHeartRateForNotification(user, date);
    }

    @Override
    public String notificationMessage(User user, LocalDateTime today) {
        if (user.getNotification().getIsHeartRateAlertOn()) {
            if (getHeartRateForNotification(user, today) < user.getNotification().getHeartRateMeasurementFrequency()) {
                return NotificationMessage.HEART_RATE.getMessage();
            }
        }
        return null;
    }
}