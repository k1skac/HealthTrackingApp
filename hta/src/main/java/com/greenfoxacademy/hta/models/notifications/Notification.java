package com.greenfoxacademy.hta.models.notifications;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.hta.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @CreationTimestamp
    private LocalDate createdAt;
    private Boolean isHeartRateAlertOn;
    private Integer heartRateMeasurementFrequency;
    private Boolean isBloodPressureAlertOn;
    private Integer bloodPressureMeasurementFrequency;
    private Boolean isWeightAlertOn;
    private Integer weightMeasurementFrequency;
    private Boolean isMedicationAlertOn;
    @JsonIgnore
    @OneToOne(mappedBy = "notification")
    private User user;

    public Notification(boolean isHeartRateAlertOn, int heartRateMeasurementFrequency, boolean isBloodPressureAlertOn, int bloodPressureMeasurementFrequency, boolean isWeightAlertOn, int weightMeasurementFrequency, boolean isMedicationAlertOn) {
        this.isHeartRateAlertOn = isHeartRateAlertOn;
        this.heartRateMeasurementFrequency = heartRateMeasurementFrequency;
        this.isBloodPressureAlertOn = isBloodPressureAlertOn;
        this.bloodPressureMeasurementFrequency = bloodPressureMeasurementFrequency;
        this.isWeightAlertOn = isWeightAlertOn;
        this.weightMeasurementFrequency = weightMeasurementFrequency;
        this.isMedicationAlertOn = isMedicationAlertOn;
    }
}