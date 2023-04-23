package com.greenfoxacademy.hta.repositories.medications;

import com.greenfoxacademy.hta.models.medication.Medication;
import com.greenfoxacademy.hta.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IMedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByUserAndIsActiveIsTrueAndPeriodEndIsAfter(User user, LocalDateTime periodEnd);
}