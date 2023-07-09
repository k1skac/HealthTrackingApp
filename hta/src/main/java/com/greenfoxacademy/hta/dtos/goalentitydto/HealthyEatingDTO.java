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
    public HealthyEatingDTO(String description, float calorieLimit, LocalDateTime deadline) {
        this.description = description;
        this.calorieLimit = calorieLimit;
        this.deadline = deadline;
    }
    public HealthyEatingDTO(float calorieLimit) {

        this.calorieLimit = calorieLimit;
    }
    public HealthyEatingDTO(String description) {

        this.description = description;
    }
    public HealthyEatingDTO(LocalDateTime deadline) {

        this.deadline = deadline;
    }
   /* public HealthyEatingDTO(LocalDateTime startDate) {

        this.startDate = startDate;
    }*/


}
