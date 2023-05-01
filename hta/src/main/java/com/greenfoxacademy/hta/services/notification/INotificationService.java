package com.greenfoxacademy.hta.services.notification;

import com.greenfoxacademy.hta.dtos.notificationdto.NotificationRequestDTO;
import com.greenfoxacademy.hta.dtos.notificationdto.NotificationResponseDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.models.user.User;

public interface INotificationService {
    NotificationResponseDTO add(User user, NotificationRequestDTO notificationRequestDTO) throws HtaException;
    NotificationResponseDTO update(User user, NotificationRequestDTO notificationRequestDTO) throws HtaException;
}