package com.greenfoxacademy.hta.dtos.goalentitydto;

import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeightLostDTO {
    private String description;
    private float goalWeight;
    private Date deadline;
}
