package com.greenfoxacademy.hta.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.hta.models.filemanagement.FileData;
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

    @CreatedDate
    private LocalDateTime weightMeasuredAt;
    private float weight;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToOne()
    private FileData fileData;

    public Weight(LocalDateTime measureTime, float weight) {
        this.weightMeasuredAt = measureTime;
        this.weight = weight;
    }
}
