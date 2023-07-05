package com.greenfoxacademy.hta.dtos.saveuserdatadto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveCalciumDTO {
    private LocalDateTime measureTime;
    @Min(value = 0, message = "The specified calcium value is too low! See a doctor as soon as possible!  Or check that the value is correct!")
    @Max(value = 6, message = "The specified calcium value is too high! See a doctor as soon as possible!  Or check that the value is correct!")
    private double calciumValue;
}
