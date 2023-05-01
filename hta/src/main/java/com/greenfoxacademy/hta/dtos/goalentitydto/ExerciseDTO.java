package com.greenfoxacademy.hta.dtos.goalentitydto;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDTO {
    private String description;
    private Timestamp dailyActiveTime;
    private Date deadline;
}
