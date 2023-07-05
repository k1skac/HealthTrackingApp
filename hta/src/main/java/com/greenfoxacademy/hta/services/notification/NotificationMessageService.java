package com.greenfoxacademy.hta.services.notification;

import com.greenfoxacademy.hta.dtos.notificationdto.*;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.services.bloodpressure.IBloodPressureService;
import com.greenfoxacademy.hta.services.heartrate.IHeartRateService;
import com.greenfoxacademy.hta.services.medication.IMedicationIntakeService;
import com.greenfoxacademy.hta.services.healthylivingservices.IWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationMessageService implements INotificationMessageService {
    private final IBloodPressureService iBloodPressureService;
    private final IHeartRateService iHeartRateService;
    private final IWeightService iWeightService;
    private final IMedicationIntakeService iMedicationIntakeService;
    private final LocalDateTime TODAY = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));

    @Autowired
    public NotificationMessageService(IBloodPressureService iBloodPressureService,
                                      IHeartRateService iHeartRateService,
                                      IWeightService iWeightService,
                                      IMedicationIntakeService iMedicationIntakeService) {
        this.iBloodPressureService = iBloodPressureService;
        this.iHeartRateService = iHeartRateService;
        this.iWeightService = iWeightService;
        this.iMedicationIntakeService = iMedicationIntakeService;
    }

    @Override
    public NotificationMessageDTO createNotifications(User user) {
        List<String> notificationMessages = new ArrayList<>();

        String message = iBloodPressureService.notificationMessage(user, TODAY);
        if (message != null) notificationMessages.add(message);

        message = iHeartRateService.notificationMessage(user, TODAY);
        if (message != null) notificationMessages.add(message);

        message = iWeightService.notificationMessage(user, TODAY);
        if (message != null) notificationMessages.add(message);

        List<String> messages = iMedicationIntakeService.notificationMessages(user, TODAY);
        if (!messages.isEmpty()) notificationMessages.addAll(messages);

        return new NotificationMessageDTO(notificationMessages);
    }

    @Override
    public WeightNotificationMessageDTO createWeightNotification(User user) {
        return new WeightNotificationMessageDTO(iWeightService.notificationMessage(user, TODAY));
    }

    @Override
    public HeartRateNotificationMessageDTO createHeartRateNotification(User user) {
        return new HeartRateNotificationMessageDTO(iHeartRateService.notificationMessage(user, TODAY));
    }

    @Override
    public BloodPressureNotificationMessageDTO createBloodPressureNotification(User user) {
        return new BloodPressureNotificationMessageDTO(iBloodPressureService.notificationMessage(user, TODAY));
    }

    @Override
    public MedicationNotificationMessageDTO createMedicationNotification(User user) {
        return new MedicationNotificationMessageDTO(iMedicationIntakeService.notificationMessages(user, TODAY));
    }
}