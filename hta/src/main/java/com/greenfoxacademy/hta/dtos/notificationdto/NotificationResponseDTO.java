package com.greenfoxacademy.hta.dtos.notificationdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationResponseDTO {
    private String email;
    private Boolean isHeartRateAlertOn;
    private Integer heartRateMeasurementFrequency;
    private Boolean isBloodPressureAlertOn;
    private Integer bloodPressureMeasurementFrequency;
    private Boolean isMedicationAlertOn;
    private Boolean isWeightAlertOn;
    private Integer weightMeasurementFrequency;
}
