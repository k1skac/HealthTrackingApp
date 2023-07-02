package com.greenfoxacademy.hta.dtos.reportsdto.health;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseSimpleBloodPressureDTO {

    private LocalDateTime bloodPressureMeasuredAt;
    private float systolic;
    private float diastolic;

}
