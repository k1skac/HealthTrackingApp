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
public class HealthyEating {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private float calorieLimit;
    private LocalDateTime deadline;
    private LocalDateTime startDate;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Goal goal;

    public HealthyEating(String description, float calorieLimit, LocalDateTime deadline, LocalDateTime startDate) {
        this.description = description;
        this.calorieLimit = calorieLimit;
        this.deadline = deadline;
        this.startDate = startDate;
    }
}