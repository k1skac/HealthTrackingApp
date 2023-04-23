package com.greenfoxacademy.hta.models;

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

  public HeartRate(LocalDateTime measureTime, float heartRate) {
    this.measureTime = measureTime;
    this.heartRate = heartRate;
  }
}
