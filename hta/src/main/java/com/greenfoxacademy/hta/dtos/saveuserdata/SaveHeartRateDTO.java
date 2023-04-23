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
public class SaveHeartRateDTO {
    private LocalDateTime measureTime;
    @Min(20)
    @Max(170)
    private float heartRate;
}
