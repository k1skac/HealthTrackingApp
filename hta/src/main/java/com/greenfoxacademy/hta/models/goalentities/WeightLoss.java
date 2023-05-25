package com.greenfoxacademy.hta.models.goalentities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime deadline;
    private LocalDateTime startDate;
    @JsonIgnore
    @OneToOne (cascade = CascadeType.ALL)
    private Goal goal;

    public WeightLoss(String description, float goalWeight, LocalDateTime deadline, Goal goal, LocalDateTime startDate) {
        this.description = description;
        this.goalWeight = goalWeight;
        this.deadline = deadline;
        this.goal = goal;
        this.startDate = startDate;
    }

    public WeightLoss(String description, float goalWeight, LocalDateTime deadline, LocalDateTime startDate) {
        this.description = description;
        this.goalWeight = goalWeight;
        this.deadline = deadline;
        this.startDate = startDate;
    }
}