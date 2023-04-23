package com.greenfoxacademy.hta.dtos.goalentitydto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoalDTO {
    private String goalDescription;
    private WeightLostDTO weightLost = null;
    private HealthyEatingDTO calorieIntakeLimit = null;
    private ExerciseDTO exercise = null;

    public GoalDTO(String descriptionDTO, WeightLostDTO weightLostDTO) {
        this.goalDescription = descriptionDTO;
        this.weightLost = weightLostDTO;
    }
}