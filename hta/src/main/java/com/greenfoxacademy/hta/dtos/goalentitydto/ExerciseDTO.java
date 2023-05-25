package com.greenfoxacademy.hta.dtos.goalentitydto;

import lombok.*;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDTO {
    private String description;
    private Long dailyActiveTimeInMinutes;
    private LocalDateTime deadline;
    private LocalDateTime startDate;
}
