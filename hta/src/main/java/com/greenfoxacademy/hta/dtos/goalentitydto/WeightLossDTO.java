package com.greenfoxacademy.hta.dtos.goalentitydto;

import lombok.*;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeightLossDTO {
    private String description;
    private float goalWeight;
    private LocalDateTime deadline;
    private LocalDateTime startDate;
}
