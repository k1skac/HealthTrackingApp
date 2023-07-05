package com.greenfoxacademy.hta.services.heartrate;

import com.greenfoxacademy.hta.dtos.saveuserdatadto.SaveHeartRateDTO;
import com.greenfoxacademy.hta.exceptions.HeartRateBadRequestException;
import com.greenfoxacademy.hta.models.filemanagement.FileData;
import com.greenfoxacademy.hta.models.user.HeartRate;
import com.greenfoxacademy.hta.models.notifications.NotificationMessage;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.IHeartRateRepository;
import com.greenfoxacademy.hta.services.filemanagment.IFileDataService;
import com.greenfoxacademy.hta.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class HeartRateService implements IHeartRateService {
    private final IHeartRateRepository iHeartRateRepository;
    private final IUserService iUserService;
    private final IFileDataService iFileDataService;

    @Autowired
    public HeartRateService(IHeartRateRepository iHeartRateRepository, IUserService iUserService, IFileDataService iFileDataService) {
        this.iHeartRateRepository = iHeartRateRepository;
        this.iUserService = iUserService;
        this.iFileDataService = iFileDataService;
    }

    @Override
    public int getHeartRateForNotification(User user, LocalDateTime date) {
        return iHeartRateRepository.findHeartRateForNotification(user, date);
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
    public void save(SaveHeartRateDTO saveHeartRateDTO, Authentication authentication) throws HeartRateBadRequestException, IOException {
        if (saveHeartRateDTO.getHeartRate() == 0f || saveHeartRateDTO.getHeartRateMeasuredAt() == null) {
            throw new HeartRateBadRequestException();
        }

        User user = iUserService.findByEmail(authentication.getName());
        HeartRate heartRate = new HeartRate(saveHeartRateDTO.getHeartRate(), saveHeartRateDTO.getHeartRateMeasuredAt());
        heartRate.setUser(user);
        iHeartRateRepository.save(heartRate);

        if (saveHeartRateDTO.getHeartRateFile() != null) {
            FileData uploadedFile = iFileDataService.uploadFileDataToDirectory(saveHeartRateDTO.getHeartRateFile());
            FileData fileData = iFileDataService.save(uploadedFile);

            heartRate.setFileData(fileData);
            iHeartRateRepository.save(heartRate);
        }
    }
}