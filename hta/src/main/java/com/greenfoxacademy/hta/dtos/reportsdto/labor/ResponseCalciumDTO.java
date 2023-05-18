package com.greenfoxacademy.hta.dtos.reportsdto.labor;

import com.greenfoxacademy.hta.models.BloodLabDataLimits;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseCalciumDTO {

    private double valueOfCalcium;
    private LocalDateTime measureTime;
    private double maxCalciumValue;
    private double minCalciumValue;
    private String warning;

    public ResponseCalciumDTO() {
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        this.maxCalciumValue = bloodLabDataLimits.getMaxCalciumValue();
        this.minCalciumValue = bloodLabDataLimits.getMinCalciumValue();
    }
}
