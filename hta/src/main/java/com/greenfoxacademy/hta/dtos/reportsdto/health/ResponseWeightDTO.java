package com.greenfoxacademy.hta.dtos.reportsdto.health;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWeightDTO {
    private double height;
    private String BodyMassIndex;
}
