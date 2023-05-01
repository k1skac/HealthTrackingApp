package com.greenfoxacademy.hta.models.goalentities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

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
    private Timestamp dailyActiveTime;
    private Date deadline;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Goal goal;

    public Exercise(String description, Timestamp dailyActiveTime, Date deadline) {
        this.description = description;
        this.dailyActiveTime = dailyActiveTime;
        this.deadline = deadline;
    }
}

