package com.greenfoxacademy.hta.services.notification;

import com.greenfoxacademy.hta.dtos.notifications.NotificationRequestDTO;
import com.greenfoxacademy.hta.dtos.notifications.NotificationResponseDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.models.User;

public interface INotificationService {
    NotificationResponseDTO add(User user, NotificationRequestDTO notificationRequestDTO) throws HtaException;
    NotificationResponseDTO update(User user, NotificationRequestDTO notificationRequestDTO) throws HtaException;
}