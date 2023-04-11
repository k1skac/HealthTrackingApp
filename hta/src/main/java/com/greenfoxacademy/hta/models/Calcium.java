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
public class Calcium {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private double value;
  private double maxNormalValue;
  private double minNormalValue;
  private LocalDateTime measureTime;
  @ManyToOne(fetch = FetchType.LAZY)
  private BloodLabData bloodLabData;

}
