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

    @Max(value = 260, message = "The specified systolic value is too high! See a doctor as soon as possible!  Or check that the value is correct!")
    @Min(value = 50, message = "The specified systolic value is too low! See a doctor as soon as possible!  Or check that the value is correct!")
    private float systolic;
    @Min(value = 30, message = "The specified diastolic value is too low! See a doctor as soon as possible!  Or check that the value is correct!")
    @Max(value = 250, message = "The specified diastolic value is too high! See a doctor as soon as possible!  Or check that the value is correct!")
    private float diastolic;
    private MultipartFile bloodPressureFile;
}
