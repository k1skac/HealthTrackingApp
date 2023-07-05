package com.greenfoxacademy.hta.services.notification;

import com.greenfoxacademy.hta.dtos.notificationdto.*;
import com.greenfoxacademy.hta.models.medication.Medication;
import com.greenfoxacademy.hta.models.user.User;

public interface INotificationMessageService {
    NotificationMessageDTO createNotifications(User user);
    WeightNotificationMessageDTO createWeightNotification(User user);
    HeartRateNotificationMessageDTO createHeartRateNotification(User user);
    BloodPressureNotificationMessageDTO createBloodPressureNotification(User user);
    MedicationNotificationMessageDTO createMedicationNotification(User user);
}