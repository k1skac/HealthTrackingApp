package com.greenfoxacademy.hta.models.goalentities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.hta.models.goalentities.Goal;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HealthyEating {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private float calorieLimit;
    private Date deadline;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Goal goal;

    public HealthyEating(String description, float calorieLimit, Date deadline) {
        this.description =description;
        this.calorieLimit = calorieLimit;
        this.deadline = deadline;
    }
}