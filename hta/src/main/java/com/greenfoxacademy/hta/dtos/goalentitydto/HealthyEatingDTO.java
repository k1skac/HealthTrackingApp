package com.greenfoxacademy.hta.dtos.goalentitydto;

import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HealthyEatingDTO {
    private String description;
    private float calorieLimit;
    private Date deadline;
}
