package com.greenfoxacademy.hta.repositories.medications;

import com.greenfoxacademy.hta.models.medication.MedicationIntake;
import com.greenfoxacademy.hta.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface IMedicationIntakeRepository extends JpaRepository<MedicationIntake, Long> {
    @Query("SELECT COUNT(medicationIntake) FROM MedicationIntake medicationIntake WHERE medicationIntake.medication.user = ?1 " +
            "AND medicationIntake.takenAt >= ?2 AND medicationIntake.medication.name = ?3")
    int findMedicationIntakeForNotification(User user, LocalDateTime today, String medicationName);
}