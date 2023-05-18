package com.greenfoxacademy.hta.dtos.reportsdto.labor;

import com.greenfoxacademy.hta.models.BloodLabDataLimits;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseGlucoseDTO {

    private double valueOfGlucose;
    private LocalDateTime measureTime;
    private double maxGlucoseValue;
    private double minGlucoseValue;
    private String warning;

    public ResponseGlucoseDTO() {
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        this.maxGlucoseValue = bloodLabDataLimits.getMaxGlucoseValue();
        this.minGlucoseValue = bloodLabDataLimits.getMinGlucoseValue();
    }
}
