package com.greenfoxacademy.hta.dtos.healthylivingentitydto;

import lombok.*;

import java.sql.Timestamp;
import java.time.Duration;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class ExerciseTimeDTO {
    private Long dailyActiveTimeInMinutes;
    private float burntCalorie;
    public ExerciseTimeDTO (Long dailyActiveTimeInMinutes, float burntCalorie) {
        this.dailyActiveTimeInMinutes = dailyActiveTimeInMinutes;
        this.burntCalorie = burntCalorie;
    }
}
