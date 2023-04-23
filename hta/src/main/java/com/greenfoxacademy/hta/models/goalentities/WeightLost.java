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
public class WeightLost {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private float goalWeight;
    private Date deadline;
    @JsonIgnore
    @OneToOne (cascade = CascadeType.ALL)
    private Goal goal;

    public WeightLost(String description, float goalWeight, Date deadline, Goal goal) {
        this.description = description;
        this.goalWeight = goalWeight;
        this.deadline = deadline;
        this.goal = goal;
    }

    public WeightLost(String description, float goalWeight, Date deadline) {
        this.description = description;
        this.goalWeight = goalWeight;
        this.deadline = deadline;
    }
}