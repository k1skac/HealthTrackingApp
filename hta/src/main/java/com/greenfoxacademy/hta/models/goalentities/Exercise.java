package com.greenfoxacademy.hta.models.goalentities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.hta.models.TimeUnit;
import com.greenfoxacademy.hta.models.goalentities.Goal;
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
    //Daily active Times Goal
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Timestamp dailyActiveTime;
    /*Starting date, void schedule(TimerTask task, Date firstTime, long period)
    @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "exercise")
    private List<Timer> timers;*/
    private Date deadline;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Goal goal;

    public Exercise(String description, int time, Timestamp dailyActiveTime) {
        this.description = description;
        this.dailyActiveTime = dailyActiveTime;
    }

    public Exercise(String description, Timestamp dailyActiveTime, Date deadline) {
        this.description = description;
        this.dailyActiveTime = dailyActiveTime;
        this.deadline = deadline;
    }
}

