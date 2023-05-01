package com.greenfoxacademy.hta.services.heartrate;

import com.greenfoxacademy.hta.models.user.HeartRate;
import com.greenfoxacademy.hta.models.notifications.NotificationMessage;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.IHeartRateRepository;
import com.greenfoxacademy.hta.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HeartRateService implements IHeartRateService {
    private final IHeartRateRepository heartRateRepository;
    private final IUserService iUserService;

    @Autowired
    public HeartRateService(IHeartRateRepository heartRateRepository, IUserService iUserService) {
        this.heartRateRepository = heartRateRepository;
        this.iUserService = iUserService;
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

    @Override
    public void save(HeartRate heartRate, String email) {
        User user = iUserService.findByEmail(email);
        heartRate.setUser(user);
        heartRateRepository.save(heartRate);
        iUserService.saveUser(user);
    }
}