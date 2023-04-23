package com.greenfoxacademy.hta.dtos.saveuserdata;

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
    @Min(0)
    @Max(6)
    private double calciumValue;
}
