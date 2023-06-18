package com.greenfoxacademy.hta.services.heartrate;

import com.greenfoxacademy.hta.dtos.saveuserdatadto.SaveHeartRateDTO;
import com.greenfoxacademy.hta.models.user.HeartRate;
import com.greenfoxacademy.hta.models.user.User;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;

public interface IHeartRateService {
    int getHeartRateForNotification(User user, LocalDateTime date);
    String notificationMessage(User user, LocalDateTime today);
    void save(SaveHeartRateDTO saveHeartRateDTO, Authentication authentication) throws Exception;
}