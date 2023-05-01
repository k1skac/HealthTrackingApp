package com.greenfoxacademy.hta.models.healthylivingentities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.hta.models.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailyCalorieIntake {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @CreatedDate
    private LocalDateTime creationDate;
    private float calorie;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    public DailyCalorieIntake (float calorie, User user) {
        this.calorie = calorie;
        this.user = user;
    }
    public DailyCalorieIntake (Long id, float calorie) {
        this.id = id;
        this.calorie = calorie;
    }
}