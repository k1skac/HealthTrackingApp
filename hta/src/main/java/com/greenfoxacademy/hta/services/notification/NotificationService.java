package com.greenfoxacademy.hta.services.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.hta.dtos.notifications.NotificationRequestDTO;
import com.greenfoxacademy.hta.dtos.notifications.NotificationResponseDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.exceptions.NotificationConflictException;
import com.greenfoxacademy.hta.exceptions.NotificationNotFoundException;
import com.greenfoxacademy.hta.models.notifications.Notification;
import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.repositories.INotificationRepository;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService implements INotificationService {
    private final INotificationRepository iNotificationRepository;
    private final IUserRepository iUserRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public NotificationService(INotificationRepository iNotificationRepository, IUserRepository iUserRepository, ObjectMapper objectMapper) {
        this.iNotificationRepository = iNotificationRepository;
        this.iUserRepository = iUserRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public NotificationResponseDTO add(User user, NotificationRequestDTO notificationRequestDTO) throws HtaException {
        Optional<Notification> notification = iNotificationRepository.findByUser(user);
        if (notification.isPresent()) throw new NotificationConflictException("Notification already exists!");

        Notification newNotification = objectMapper.convertValue(notificationRequestDTO, Notification.class);
        iNotificationRepository.save(newNotification);
        user.setNotification(newNotification);
        iUserRepository.save(user);

        NotificationResponseDTO notificationResponseDTO = objectMapper.convertValue(newNotification, NotificationResponseDTO.class);
        notificationResponseDTO.setEmail(user.getEmail());

        return notificationResponseDTO;
    }

    @Override
    public NotificationResponseDTO update(User user, NotificationRequestDTO notificationRequestDTO) throws HtaException {
        Optional<Notification> notification = iNotificationRepository.findByUser(user);
        if (notification.isEmpty()) throw new NotificationNotFoundException("Notification does not exist!");

        if (notificationRequestDTO.getIsHeartRateAlertOn() != null) notification.get().setIsHeartRateAlertOn(notificationRequestDTO.getIsHeartRateAlertOn());
        if (notificationRequestDTO.getHeartRateMeasurementFrequency() != null) notification.get().setHeartRateMeasurementFrequency(notificationRequestDTO.getHeartRateMeasurementFrequency());
        if (notificationRequestDTO.getIsBloodPressureAlertOn() != null) notification.get().setIsBloodPressureAlertOn(notificationRequestDTO.getIsBloodPressureAlertOn());
        if (notificationRequestDTO.getBloodPressureMeasurementFrequency() != null) notification.get().setBloodPressureMeasurementFrequency(notificationRequestDTO.getBloodPressureMeasurementFrequency());
        if (notificationRequestDTO.getIsMedicationAlertOn() != null) notification.get().setIsMedicationAlertOn(notificationRequestDTO.getIsMedicationAlertOn());
        if (notificationRequestDTO.getIsWeightAlertOn() != null) notification.get().setIsWeightAlertOn(notificationRequestDTO.getIsWeightAlertOn());
        if (notificationRequestDTO.getWeightMeasurementFrequency() != null) notification.get().setWeightMeasurementFrequency(notificationRequestDTO.getWeightMeasurementFrequency());
        iNotificationRepository.save(notification.get());

        NotificationResponseDTO notificationResponseDTO = objectMapper.convertValue(notification.get(), NotificationResponseDTO.class);
        notificationResponseDTO.setEmail(user.getEmail());

        return notificationResponseDTO;
    }
}
