package com.greenfoxacademy.hta.models.medication;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MedicationIntake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime takenAt;
    @ManyToOne(fetch = FetchType.LAZY)
    private Medication medication;

    public MedicationIntake(LocalDateTime takenAt) {
        this.takenAt = takenAt;
    }
}
