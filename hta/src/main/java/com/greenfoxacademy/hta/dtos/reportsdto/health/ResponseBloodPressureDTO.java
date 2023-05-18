package com.greenfoxacademy.hta.dtos.reportsdto.health;

import com.greenfoxacademy.hta.models.BloodLabDataLimits;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseBloodPressureDTO {

    private LocalDateTime measureTime;
    private float systolic;
    private float diastolic;
    private double systolicMax;
    private double diastiolicMin;
    private String warning;

    public ResponseBloodPressureDTO() {
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        this.systolic = bloodLabDataLimits.getSystolicMax();
        this.diastiolicMin = bloodLabDataLimits.getDiastiolicMin();
    }
}
