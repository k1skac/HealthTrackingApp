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
public class SaveGlucoseDTO {
    private LocalDateTime measureTime;
    @Min(0)
    @Max(20)
    private double glucoseValue;
}
