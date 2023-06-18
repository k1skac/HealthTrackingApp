package com.greenfoxacademy.hta.dtos.saveuserdatadto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveBloodPressureDTO {
    private LocalDateTime bloodPressureMeasuredAt;
    @Max(260)
    private float systolic;
    @Min(30)
    private float diastolic;
    private MultipartFile bloodPressureFile;
}
