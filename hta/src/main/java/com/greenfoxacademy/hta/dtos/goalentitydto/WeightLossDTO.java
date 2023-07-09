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
    public WeightLossDTO(String description, float goalWeight, LocalDateTime deadline) {
        this.description = description;
        this.goalWeight = goalWeight;
        this.deadline = deadline;
    }
    public WeightLossDTO(String description) {
        this.description = description;
    }
    public WeightLossDTO(String description, float goalWeight) {
        this.description = description;
        this.goalWeight = goalWeight;
    }
    public WeightLossDTO(String description, LocalDateTime deadline) {
        this.description = description;
        this.deadline = deadline;
    }
    /*public WeightLossDTO(String description, LocalDateTime startDate) {
        this.description = description;
        this.startDate = startDate;
    }*/
    public WeightLossDTO(float goalWeight, LocalDateTime startDate) {
        this.goalWeight = goalWeight;
        this.startDate = startDate;
    }
   /* public WeightLossDTO(float goalWeight, LocalDateTime deadline) {
        this.goalWeight = goalWeight;
        this.deadline = deadline;
    }*/

}

