package com.greenfoxacademy.hta.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private LocalDateTime heartRateMeasuredAt;
    private float heartRate;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public HeartRate(float heartRate, LocalDateTime heartRateMeasuredAt) {
        this.heartRate = heartRate;
        this.heartRateMeasuredAt = heartRateMeasuredAt;
    }
}
