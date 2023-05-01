package com.greenfoxacademy.hta.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.hta.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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
    @JsonIgnore
    @CreatedDate
    private LocalDateTime creationDate;
    private float weight;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Weight(LocalDateTime measureTime, float weight) {
        this.creationDate = measureTime;
        this.weight = weight;
    }
}
