package com.greenfoxacademy.hta.dtos.healthylivingentitydto;

import lombok.*;

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
