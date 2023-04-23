package com.greenfoxacademy.hta.services.notification;

import com.greenfoxacademy.hta.dtos.notifications.NotificationMessageDTO;
import com.greenfoxacademy.hta.models.User;

public interface INotificationMessageService {
    NotificationMessageDTO createNotifications(User user);
}