package com.greenfoxacademy.hta.models.user;

import com.greenfoxacademy.hta.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HeartRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime measureTime;
    private float heartRate;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public HeartRate(float heartRate, LocalDateTime measureTime) {
        this.heartRate = heartRate;
        this.measureTime = measureTime;
    }
}
