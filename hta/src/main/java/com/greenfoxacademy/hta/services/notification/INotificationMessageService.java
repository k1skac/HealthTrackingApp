package com.greenfoxacademy.hta.services.notification;

import com.greenfoxacademy.hta.dtos.notificationdto.NotificationMessageDTO;
import com.greenfoxacademy.hta.models.user.User;

public interface INotificationMessageService {
    NotificationMessageDTO createNotifications(User user);
}