package com.greenfoxacademy.hta.models.healthylivingentities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.hta.models.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

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
    private LocalDateTime creationDate = LocalDateTime.now();
    private Duration dailyActiveTime;
    private float burntCalorie;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public ExerciseTime(Long minutes, float burntCalorie, User user) {
        this.dailyActiveTime = Duration.ofMinutes(minutes);
        this.burntCalorie = burntCalorie;
        this.user = user;
    }
}