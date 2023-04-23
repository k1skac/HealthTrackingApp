package com.greenfoxacademy.hta.services.bloodpressure;

import com.greenfoxacademy.hta.models.notifications.NotificationMessage;
import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.repositories.IBloodPressureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BloodPressureService implements IBloodPressureService {
    private final IBloodPressureRepository bloodPressureRepository;

    @Autowired
    public BloodPressureService(IBloodPressureRepository bloodPressureRepository) {
        this.bloodPressureRepository = bloodPressureRepository;
    }

    @Override
    public int getBloodPressureForNotification(User user, LocalDateTime date) {
        return bloodPressureRepository.findBloodPressureForNotification(user, date);
    }

    @Override
    public String notificationMessage(User user, LocalDateTime today) {
        if (user.getNotification().getIsBloodPressureAlertOn()) {
            if (getBloodPressureForNotification(user, today) < user.getNotification().getBloodPressureMeasurementFrequency()) {
                return NotificationMessage.BLOOD_PRESSURE.getMessage();
            }
        }
        return null;
    }
}
