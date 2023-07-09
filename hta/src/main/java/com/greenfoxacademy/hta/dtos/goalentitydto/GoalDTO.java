package com.greenfoxacademy.hta.dtos.goalentitydto;

import com.greenfoxacademy.hta.models.goalentities.Exercise;
import com.greenfoxacademy.hta.models.goalentities.HealthyEating;
import com.greenfoxacademy.hta.models.goalentities.WeightLoss;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoalDTO {
    private String goalDescription;
    private WeightLossDTO weightLoss;
    private HealthyEatingDTO calorieIntakeLimit;
    private ExerciseDTO exercise;

    public GoalDTO(String goalDescription, WeightLossDTO weightLoss) {
        this.goalDescription = goalDescription;
        this.weightLoss = weightLoss;
    }
    public GoalDTO(String goalDescription, HealthyEatingDTO calorieIntakeLimit) {
        this.goalDescription = goalDescription;
        this.calorieIntakeLimit = calorieIntakeLimit;
    }

    public GoalDTO(String goalDescription, ExerciseDTO exercise) {
        this.goalDescription = goalDescription;
        this.exercise = exercise;
    }

    public GoalDTO(String goalDescription,WeightLossDTO weightLoss, HealthyEatingDTO calorieIntakeLimit) {
        this.goalDescription = goalDescription;
        this.weightLoss = weightLoss;
        this.calorieIntakeLimit = calorieIntakeLimit;
    }

    public GoalDTO(String goalDescription,WeightLossDTO weightLoss, ExerciseDTO exercise) {
        this.goalDescription = goalDescription;
        this.weightLoss = weightLoss;
        this.exercise = exercise;
    }

    public GoalDTO(String goalDescription,HealthyEatingDTO calorieIntakeLimit, ExerciseDTO exercise) {
        this.goalDescription = goalDescription;
        this.calorieIntakeLimit = calorieIntakeLimit;
        this.exercise = exercise;
    }
    public GoalDTO(WeightLossDTO weightLoss) {

        this.weightLoss = weightLoss;
    }

    public GoalDTO(HealthyEatingDTO calorieIntakeLimit) {
        this.calorieIntakeLimit = calorieIntakeLimit;
    }

    public GoalDTO(ExerciseDTO exercise) {

        this.exercise = exercise;
    }

    public GoalDTO(WeightLossDTO weightLoss, HealthyEatingDTO calorieIntakeLimit) {
        this.weightLoss = weightLoss;
        this.calorieIntakeLimit = calorieIntakeLimit;
    }

    public GoalDTO(WeightLossDTO weightLoss, ExerciseDTO exercise) {
        this.weightLoss = weightLoss;
        this.exercise = exercise;
    }

    public GoalDTO(HealthyEatingDTO calorieIntakeLimit, ExerciseDTO exercise) {
        this.calorieIntakeLimit = calorieIntakeLimit;
        this.exercise = exercise;
    }

    public GoalDTO(String goalDescription) {
        this.goalDescription = goalDescription;
    }
}