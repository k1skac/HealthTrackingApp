package com.greenfoxacademy.hta.dtos.goalentitydto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoalDTO {
    private String goalDescription;
    private WeightLossDTO weightLost;
    private HealthyEatingDTO calorieIntakeLimit;
    private ExerciseDTO exercise;

    public GoalDTO(String descriptionDTO, WeightLossDTO weightLossDTO) {
        this.goalDescription = descriptionDTO;
        this.weightLost = weightLossDTO;
    }
}