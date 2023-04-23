package com.greenfoxacademy.hta.dtos.saveuserdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveWeightDTO {
    private float weight;
    private LocalDateTime creationDate;
}