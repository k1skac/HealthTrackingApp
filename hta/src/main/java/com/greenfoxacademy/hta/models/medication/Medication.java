package com.greenfoxacademy.hta.models.medication;

import com.greenfoxacademy.hta.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isActive;
    private String name;
    private float dosage;
    private Units unit;
    private int dailyFrequency;
    private LocalDateTime periodEnd;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToMany(mappedBy = "medication")
    private List<MedicationIntake> medicationIntake;

    public Medication(boolean isActive, String name, float dosage, Units unit, int dailyFrequency, LocalDateTime periodEnd) {
        this.isActive = isActive;
        this.name = name;
        this.dosage = dosage;
        this.unit = unit;
        this.dailyFrequency = dailyFrequency;
        this.periodEnd = periodEnd;
    }
}
