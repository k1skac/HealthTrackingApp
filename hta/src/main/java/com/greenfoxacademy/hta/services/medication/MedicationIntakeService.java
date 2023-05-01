package com.greenfoxacademy.hta.services.medication;

import com.greenfoxacademy.hta.models.medication.Medication;
import com.greenfoxacademy.hta.models.notifications.NotificationMessage;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.medications.IMedicationIntakeRepository;
import com.greenfoxacademy.hta.repositories.medications.IMedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MedicationIntakeService implements IMedicationIntakeService {
    private final IMedicationIntakeRepository iMedicationIntakeRepository;
    private final IMedicationRepository iMedicationRepository;

    @Autowired
    public MedicationIntakeService(IMedicationIntakeRepository iMedicationIntakeRepository, IMedicationRepository iMedicationRepository) {
        this.iMedicationIntakeRepository = iMedicationIntakeRepository;
        this.iMedicationRepository = iMedicationRepository;
    }

    @Override
    public int getMedicationIntakesForNotification(User user, LocalDateTime today, String medicationName) {
        return iMedicationIntakeRepository.findMedicationIntakeForNotification(user, today, medicationName);
    }

    @Override
    public List<String> notificationMessages(User user, LocalDateTime today) {
        if (user.getNotification().getIsMedicationAlertOn()) {
            List<String> notificationMessages = new ArrayList<>();
            getActiveMedications(user, today)
                    .forEach(medication -> {
                        int medicationTakenToday = getMedicationIntakesForNotification(user, today, medication.getName());
                        if (medicationTakenToday < medication.getDailyFrequency()) {
                            notificationMessages.add(NotificationMessage.MEDICATION.getMessage() + medication.getName() + "!");
                        }
                    });
            return notificationMessages;
        }
        return null;
    }

    private List<Medication> getActiveMedications(User user, LocalDateTime today) {
        return iMedicationRepository.findByUserAndIsActiveIsTrueAndPeriodEndIsAfter(user, today);
    }
}
