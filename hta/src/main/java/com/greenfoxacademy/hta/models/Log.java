package com.greenfoxacademy.hta.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Log {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreationTimestamp
  private LocalDateTime logCreatedAt;
  private String event;
  private String description;
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;
  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER)
  private LogType logType;

  public Log(User user, LogType logType, String description) {
    this.event= logType.getMessage()+user.getUsername();
    this.user=user;
    this.logType=logType;
    this.description=description;
  }
//
}
