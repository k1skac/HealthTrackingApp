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
public class SaveWeightDTO {
    @Min(value = 10, message = "The specified weight value is too low! See a doctor as soon as possible!  Or check that the value is correct!")
    @Max(value = 500, message = "The specified weight value is too high! See a doctor as soon as possible!  Or check that the value is correct!")
    private float weight;
    private LocalDateTime weightMeasuredAt;
    private MultipartFile weightFile;
}
