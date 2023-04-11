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
public class Glucose {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private double value;
  private static final double maxNormalValue = 6.0;
  private static final double minNormalValue = 3.0;
  private LocalDateTime measureTime;
  @ManyToOne(fetch = FetchType.LAZY)
  private BloodLabData bloodLabData;

}
