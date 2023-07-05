package com.greenfoxacademy.hta.services.bloodpressure;

import com.greenfoxacademy.hta.dtos.saveuserdatadto.SaveBloodPressureDTO;
import com.greenfoxacademy.hta.exceptions.BloodPressureBadRequestException;
import com.greenfoxacademy.hta.models.user.BloodPressure;
import com.greenfoxacademy.hta.models.user.User;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.time.LocalDateTime;

public interface IBloodPressureService {
    int getBloodPressureForNotification(User user, LocalDateTime date);
    String notificationMessage(User user, LocalDateTime today);
    void save(SaveBloodPressureDTO saveBloodPressureDTO, Authentication authentication) throws IOException, BloodPressureBadRequestException;
}