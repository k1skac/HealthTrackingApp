package com.greenfoxacademy.hta.dtos.reportsdto.health;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWeightPeriodDTO {
    private LocalDateTime weightMeasuredAt;
    private float weight;
}
