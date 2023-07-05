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
public class SaveHeartRateDTO {
    private LocalDateTime heartRateMeasuredAt;
    @Min(value = 20, message = "The specified heartrate value is too low! See a doctor as soon as possible!  Or check that the value is correct!")
    @Max(value = 170, message = "The specified heartrate value is too high! See a doctor as soon as possible!  Or check that the value is correct!")
    private float heartRate;
    private MultipartFile heartRateFile;
}
