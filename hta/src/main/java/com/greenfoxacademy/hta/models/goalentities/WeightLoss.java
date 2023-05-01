package com.greenfoxacademy.hta.models.goalentities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeightLoss {
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

    public WeightLoss(String description, float goalWeight, Date deadline, Goal goal) {
        this.description = description;
        this.goalWeight = goalWeight;
        this.deadline = deadline;
        this.goal = goal;
    }

    public WeightLoss(String description, float goalWeight, Date deadline) {
        this.description = description;
        this.goalWeight = goalWeight;
        this.deadline = deadline;
    }
}