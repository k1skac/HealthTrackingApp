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
public class Weight {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime measureTime;
  private float weight;
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  public Weight(LocalDateTime measureTime, float weight) {
    this.measureTime = measureTime;
    this.weight = weight;
  }
}
