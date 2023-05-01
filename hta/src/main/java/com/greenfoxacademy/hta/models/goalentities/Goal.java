package com.greenfoxacademy.hta.models.goalentities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.hta.models.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goal {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @CreatedDate
    private Date creationDate;
    private Boolean isActive = true;
    private String goalDescription;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "goal", cascade = CascadeType.ALL)
    private WeightLoss weightLoss;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "goal", cascade = CascadeType.ALL)
    private HealthyEating calorieIntakeLimit;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "goal", cascade = CascadeType.ALL)
    private Exercise exercise;
    @JsonIgnore
    @ManyToOne
    private User user;

    public Goal(String goalDescription, Date creationDate) {
        this.goalDescription = goalDescription;
        this.creationDate = creationDate;
    }

    public Goal(WeightLoss weightLoss) {
        this.weightLoss = weightLoss;
    }

    public Goal(HealthyEating calorieIntakeLimit) {
        this.calorieIntakeLimit = calorieIntakeLimit;
    }

    public Goal(Exercise exercise) {
        this.exercise = exercise;
    }

    public Goal(WeightLoss weightLoss, HealthyEating calorieIntakeLimit) {
        this.weightLoss = weightLoss;
        this.calorieIntakeLimit = calorieIntakeLimit;
    }

    public Goal(WeightLoss weightLoss, Exercise exercise) {
        this.weightLoss = weightLoss;
        this.exercise = exercise;
    }

    public Goal(HealthyEating calorieIntakeLimit, Exercise exercise) {
        this.calorieIntakeLimit = calorieIntakeLimit;
        this.exercise = exercise;
    }

    public Goal(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public void setWeightLoss(String description, float goalWeight, Date deadline) {
        weightLoss.setDescription(description);
        weightLoss.setGoalWeight(goalWeight);
        weightLoss.setDeadline(deadline);
    }

    public void setCalorieIntakeLimit(String description, float calorieLimit, Date deadline) {
        calorieIntakeLimit.setDescription(description);
        calorieIntakeLimit.setCalorieLimit(calorieLimit);
        calorieIntakeLimit.setDeadline(deadline);
    }

    public void setDailyActiveTime(String description, Timestamp dailyActiveTime, Date deadline) {
        exercise.setDescription(description);
        exercise.setDailyActiveTime(dailyActiveTime);
        exercise.setDeadline(deadline);
    }
}
