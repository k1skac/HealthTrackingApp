package com.greenfoxacademy.hta.models.goalentities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Duration dailyActiveTime;
    private LocalDateTime deadline;
    private LocalDateTime startDate;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Goal goal;

    public Exercise(String description, Duration dailyActiveTime, LocalDateTime deadline, LocalDateTime startDate) {
        this.description = description;
        this.dailyActiveTime = dailyActiveTime;
        this.deadline = deadline;
        this.startDate = startDate;
    }
    public Exercise(String description, Long minutes, LocalDateTime deadline, LocalDateTime startDate) {
        this.description = description;
        this.dailyActiveTime = Duration.ofMinutes(minutes);
        this.deadline = deadline;
        this.startDate = startDate;
    }

    public void setDailyActiveTime(Long minutes) {
        this.dailyActiveTime = Duration.ofMinutes(minutes);
    }
}

