package com.greenfoxacademy.hta.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserDataDTO {
        private LocalDateTime measureTime;
        @Min(0)
        @Max(20)
        private double glucoseValue;
        @Min(20)
        @Max(170)
        private float heartRate;
        @Max(260)
        private float systolic;
        @Min(30)
        private float diastolic;
    }

