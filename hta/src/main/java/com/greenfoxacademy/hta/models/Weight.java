package com.greenfoxacademy.hta.models;


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
public class Weight {
  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime measureTime;
  private float weight;
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  public Weight(LocalDateTime measureTime, float weight) {
    this.measureTime = measureTime;
    this.weight = weight;
  }
}
