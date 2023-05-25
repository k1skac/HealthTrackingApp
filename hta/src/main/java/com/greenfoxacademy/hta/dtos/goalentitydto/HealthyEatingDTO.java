package com.greenfoxacademy.hta.dtos.goalentitydto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HealthyEatingDTO {
    private String description;
    private float calorieLimit;
    private LocalDateTime deadline;
    private LocalDateTime startDate;
}
