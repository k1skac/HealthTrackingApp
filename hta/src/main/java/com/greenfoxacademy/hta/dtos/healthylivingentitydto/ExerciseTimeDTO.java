package com.greenfoxacademy.hta.dtos.healthylivingentitydto;

import lombok.*;

import java.sql.Timestamp;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseTimeDTO {
    private Timestamp dailyActiveTime;
    private float burntCalorie;
    public ExerciseTimeDTO (Long timeMilliSecond, float burntCalorie) {
        this.dailyActiveTime = new Timestamp(timeMilliSecond);
        this.burntCalorie = burntCalorie;
    }
}
