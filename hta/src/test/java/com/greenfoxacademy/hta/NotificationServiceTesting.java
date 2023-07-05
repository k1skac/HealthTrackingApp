package com.greenfoxacademy.hta;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.hta.dtos.notificationdto.NotificationRequestDTO;
import com.greenfoxacademy.hta.dtos.notificationdto.NotificationResponseDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.exceptions.NotificationConflictException;
import com.greenfoxacademy.hta.exceptions.NotificationNotFoundException;
import com.greenfoxacademy.hta.models.notifications.Notification;
import com.greenfoxacademy.hta.models.roles.Role;
import com.greenfoxacademy.hta.models.roles.RoleName;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.INotificationRepository;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.services.notification.NotificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class NotificationServiceTesting {

    @Test
    public void whenAddingNewNotificationSettings_thenOK() throws HtaException {
        // Arrange
        var iNotificationRepository = mock(INotificationRepository.class);
        var iUserRepository = mock(IUserRepository.class);
        var notificationService = new NotificationService(iNotificationRepository, iUserRepository, new ObjectMapper());
        List<Role> userRole = new ArrayList<>(List.of(
                new Role(RoleName.USER)
        ));
        User user = new User("NotificationMan", "iNotifyU@uCanCountOnMe.com", "notify123", userRole);

        NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO(
                true,
                3,
                false,
                null,
                true,
                true,
                1
        );

        //Act
        NotificationResponseDTO notificationResponseDTO = notificationService.add(user, notificationRequestDTO);

        //Assert
        Assertions.assertEquals(user.getEmail(), notificationResponseDTO.getEmail());
        Assertions.assertEquals(
                notificationRequestDTO.getIsHeartRateAlertOn(),
                notificationResponseDTO.getIsHeartRateAlertOn()
        );
        Assertions.assertEquals(
                notificationRequestDTO.getHeartRateMeasurementFrequency(),
                notificationResponseDTO.getHeartRateMeasurementFrequency()
        );
        Assertions.assertEquals(
                notificationRequestDTO.getIsBloodPressureAlertOn(),
                notificationResponseDTO.getIsBloodPressureAlertOn()
        );
        Assertions.assertEquals(
                notificationRequestDTO.getBloodPressureMeasurementFrequency(),
                notificationResponseDTO.getBloodPressureMeasurementFrequency()
        );
        Assertions.assertEquals(
                notificationRequestDTO.getIsMedicationAlertOn(),
                notificationResponseDTO.getIsMedicationAlertOn()
        );
        Assertions.assertEquals(
                notificationRequestDTO.getIsWeightAlertOn(),
                notificationResponseDTO.getIsWeightAlertOn()
        );
        Assertions.assertEquals(
                notificationRequestDTO.getWeightMeasurementFrequency(),
                notificationResponseDTO.getWeightMeasurementFrequency()
        );
    }

    @Test
    public void whenAddingNewNotificationSettings_thenAlreadyExists() {
        // Arrange
        var iNotificationRepository = mock(INotificationRepository.class);
        var iUserRepository = mock(IUserRepository.class);
        var notificationService = new NotificationService(iNotificationRepository, iUserRepository, new ObjectMapper());
        List<Role> userRole = new ArrayList<>(List.of(
                new Role(RoleName.USER)
        ));
        User user = new User("NotificationMan", "iNotifyU@uCanCountOnMe.com", "notify123", userRole);

        when(iNotificationRepository.findByUser(user)).thenReturn(Optional.of(new Notification()));

        // Act & Assert
        Assertions.assertThrows(
                NotificationConflictException.class, () -> notificationService.add(
                        user,
                        new NotificationRequestDTO()
                )
        );
    }

    @Test
    public void whenUpdatingNotificationSettings_thenOK() throws HtaException {
        // Arrange
        var iNotificationRepository = mock(INotificationRepository.class);
        var iUserRepository = mock(IUserRepository.class);
        var notificationService = new NotificationService(iNotificationRepository, iUserRepository, new ObjectMapper());
        List<Role> userRole = new ArrayList<>(List.of(
                new Role(RoleName.USER)
        ));
        User user = new User("NotificationMan", "iNotifyU@uCanCountOnMe.com", "notify123", userRole);

        NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO(
                true,
                3,
                false,
                null,
                true,
                true,
                1
        );

        when(iNotificationRepository.findByUser(user)).thenReturn(Optional.of(new Notification()));

        // Act
        NotificationResponseDTO notificationResponseDTO = notificationService.update(user, notificationRequestDTO);

        // Assert
        Assertions.assertEquals(user.getEmail(), notificationResponseDTO.getEmail());
        Assertions.assertEquals(
                notificationRequestDTO.getIsHeartRateAlertOn(),
                notificationResponseDTO.getIsHeartRateAlertOn()
        );
        Assertions.assertEquals(
                notificationRequestDTO.getHeartRateMeasurementFrequency(),
                notificationResponseDTO.getHeartRateMeasurementFrequency()
        );
        Assertions.assertEquals(
                notificationRequestDTO.getIsBloodPressureAlertOn(),
                notificationResponseDTO.getIsBloodPressureAlertOn()
        );
        Assertions.assertEquals(
                notificationRequestDTO.getBloodPressureMeasurementFrequency(),
                notificationResponseDTO.getBloodPressureMeasurementFrequency()
        );
        Assertions.assertEquals(
                notificationRequestDTO.getIsMedicationAlertOn(),
                notificationResponseDTO.getIsMedicationAlertOn()
        );
        Assertions.assertEquals(
                notificationRequestDTO.getIsWeightAlertOn(),
                notificationResponseDTO.getIsWeightAlertOn()
        );
        Assertions.assertEquals(
                notificationRequestDTO.getWeightMeasurementFrequency(),
                notificationResponseDTO.getWeightMeasurementFrequency()
        );
    }

    @Test
    public void whenUpdatingNotificationSettings_thenNotFound() {
        // Arrange
        var iNotificationRepository = mock(INotificationRepository.class);
        var iUserRepository = mock(IUserRepository.class);
        var notificationService = new NotificationService(iNotificationRepository, iUserRepository, new ObjectMapper());

        // Act & Assert
        Assertions.assertThrows(
                NotificationNotFoundException.class, () -> notificationService.update(
                        new User(),
                        new NotificationRequestDTO()
                )
        );
    }
}