package com.greenfoxacademy.hta.services.bloodpressure;

import com.greenfoxacademy.hta.dtos.saveuserdatadto.SaveBloodPressureDTO;
import com.greenfoxacademy.hta.exceptions.BloodPressureBadRequestException;
import com.greenfoxacademy.hta.models.filemanagement.FileData;
import com.greenfoxacademy.hta.models.user.BloodPressure;
import com.greenfoxacademy.hta.models.notifications.NotificationMessage;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.IBloodPressureRepository;
import com.greenfoxacademy.hta.services.filemanagment.IFileDataService;
import com.greenfoxacademy.hta.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class BloodPressureService implements IBloodPressureService {
    private final IBloodPressureRepository iBloodPressureRepository;
    private final IUserService iUserService;
    private final IFileDataService iFileDataService;

    @Autowired
    public BloodPressureService(IBloodPressureRepository iBloodPressureRepository, IUserService iUserService, IFileDataService iFileDataService) {
        this.iBloodPressureRepository = iBloodPressureRepository;
        this.iUserService = iUserService;
        this.iFileDataService = iFileDataService;
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
    public void save(SaveBloodPressureDTO saveBloodPressureDTO, Authentication authentication) throws IOException, BloodPressureBadRequestException {
        if (saveBloodPressureDTO.getSystolic() == 0f || saveBloodPressureDTO.getDiastolic() == 0f || saveBloodPressureDTO.getBloodPressureMeasuredAt() == null) {
            throw new BloodPressureBadRequestException();
        }

        User user = iUserService.findByEmail(authentication.getName());
        BloodPressure bloodPressure = new BloodPressure(saveBloodPressureDTO.getSystolic(), saveBloodPressureDTO.getDiastolic(), saveBloodPressureDTO.getBloodPressureMeasuredAt());
        bloodPressure.setUser(user);
        iBloodPressureRepository.save(bloodPressure);

        if (saveBloodPressureDTO.getBloodPressureFile() != null) {
            FileData uploadedFile = iFileDataService.uploadFileDataToDirectory(saveBloodPressureDTO.getBloodPressureFile());
            uploadedFile.setBloodPressure(bloodPressure);
            FileData fileData = iFileDataService.save(uploadedFile);
            bloodPressure.setFileData(fileData);
            iBloodPressureRepository.save(bloodPressure);
        }
    }
}