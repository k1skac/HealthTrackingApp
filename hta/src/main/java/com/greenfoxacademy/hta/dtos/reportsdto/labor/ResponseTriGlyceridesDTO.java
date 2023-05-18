package com.greenfoxacademy.hta.dtos.reportsdto.labor;

import com.greenfoxacademy.hta.models.BloodLabDataLimits;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseTriGlyceridesDTO {

    private double value;
    private LocalDateTime measureTime;
    private double maxTriGlyceridesValue;

    private String warning;

    public ResponseTriGlyceridesDTO() {
        BloodLabDataLimits bloodLabDataLimits = new BloodLabDataLimits();
        this.maxTriGlyceridesValue = bloodLabDataLimits.getMaxTriGlyceridesValue();
    }
}
