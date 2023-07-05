package com.greenfoxacademy.hta.services.heartrate;

import com.greenfoxacademy.hta.dtos.saveuserdatadto.SaveHeartRateDTO;
import com.greenfoxacademy.hta.exceptions.HeartRateBadRequestException;
import com.greenfoxacademy.hta.models.user.HeartRate;
import com.greenfoxacademy.hta.models.user.User;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.time.LocalDateTime;

public interface IHeartRateService {
    int getHeartRateForNotification(User user, LocalDateTime date);
    String notificationMessage(User user, LocalDateTime today);
    void save(SaveHeartRateDTO saveHeartRateDTO, Authentication authentication) throws HeartRateBadRequestException, IOException;
}