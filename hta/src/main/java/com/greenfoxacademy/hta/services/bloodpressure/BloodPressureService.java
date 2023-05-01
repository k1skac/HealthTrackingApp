package com.greenfoxacademy.hta.services.bloodpressure;

import com.greenfoxacademy.hta.models.user.BloodPressure;
import com.greenfoxacademy.hta.models.notifications.NotificationMessage;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.IBloodPressureRepository;
import com.greenfoxacademy.hta.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BloodPressureService implements IBloodPressureService {
    private final IBloodPressureRepository iBloodPressureRepository;
    private final IUserService iUserService;

    @Autowired
    public BloodPressureService(IBloodPressureRepository iBloodPressureRepository, IUserService iUserService) {
        this.iBloodPressureRepository = iBloodPressureRepository;
        this.iUserService = iUserService;
    }

    @Override
    public int getBloodPressureForNotification(User user, LocalDateTime date) {
        return iBloodPressureRepository.findBloodPressureForNotification(user, date);
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

    @Override
    public void save(BloodPressure bloodPressure, String email) {
        User user = iUserService.findByEmail(email);
        bloodPressure.setUser(user);
        iBloodPressureRepository.save(bloodPressure);
        iUserService.saveUser(user);
    }
}
