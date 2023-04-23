package com.greenfoxacademy.hta.models.healthylivingentities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.hta.models.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseTime {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @CreatedDate
    private LocalDateTime creationDate;
    private Timestamp dailyActiveTime;
    private float burntCalorie;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public ExerciseTime(Long timeMilliSecond, float burntCalorie, User user) {
        this.dailyActiveTime = new Timestamp(timeMilliSecond);
        this.burntCalorie = burntCalorie;
        this.user = user;
    }

    public static long convertTimeValue( String unit, int number) {
        return (number*6000);
    }
}
