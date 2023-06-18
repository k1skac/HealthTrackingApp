package com.greenfoxacademy.hta.models.filemanagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.hta.models.user.BloodPressure;
import com.greenfoxacademy.hta.models.user.HeartRate;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.models.user.Weight;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    private String filePath;
    @JsonIgnore
    @OneToOne(mappedBy = "fileData")
    private Weight weight;
    @JsonIgnore
    @OneToOne(mappedBy = "fileData")
    private BloodPressure bloodPressure;
    @JsonIgnore
    @OneToOne(mappedBy = "fileData")
    private HeartRate heartRate;

    public FileData(String fileName, String fileType, String filePath) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
    }
}

