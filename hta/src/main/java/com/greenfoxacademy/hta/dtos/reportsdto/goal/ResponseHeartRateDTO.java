package com.greenfoxacademy.hta.dtos.reportsdto.goal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseHeartRateDTO {
    private LocalDateTime measureTime;
    private float heartRate;
}
