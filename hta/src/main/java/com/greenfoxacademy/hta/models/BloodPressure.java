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
  private static final float systolicMax =140;
  private static final float diastiolicMin =80;

  public BloodPressure(float systolic, float diastolic, LocalDateTime measureTime){
    this.systolic = systolic;
    this.diastolic = diastolic;
    this.measureTime = measureTime;
  }
}
