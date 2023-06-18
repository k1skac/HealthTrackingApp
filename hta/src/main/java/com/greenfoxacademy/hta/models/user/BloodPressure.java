package com.greenfoxacademy.hta.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.hta.models.filemanagement.FileData;
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
    private LocalDateTime bloodPressureMeasuredAt;
    private float systolic;
    private float diastolic;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToOne()
    private FileData fileData;

    public BloodPressure(float systolic, float diastolic, LocalDateTime bloodPressureMeasuredAt) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.bloodPressureMeasuredAt = bloodPressureMeasuredAt;
    }
}
