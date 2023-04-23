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
public class BloodPressure {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime measureTime;
  private float systolic;
  private float diastolic;
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;
//  private static final float systolicMax = 140;
//  private static final float diastolicMin = 80;

  public BloodPressure(LocalDateTime measureTime, float systolic, float diastolic) {
    this.measureTime = measureTime;
    this.systolic = systolic;
    this.diastolic = diastolic;
  }
}
