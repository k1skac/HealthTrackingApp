package com.greenfoxacademy.hta.models.bloodlabdata;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Glucose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double value;
    private LocalDateTime measureTime;
    @ManyToOne(fetch = FetchType.LAZY)
    private BloodLabData bloodLabData;

    public Glucose(double value, LocalDateTime measureTime) {
        this.measureTime = measureTime;
        this.value = value;
    }
}
