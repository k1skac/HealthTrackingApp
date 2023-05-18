package com.greenfoxacademy.hta.dtos.reportsdto.labor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLastBloodDataLabDTO {

    private ResponseCalciumDTO lastCalciumLaborData;
    private ResponseGlucoseDTO lastGlucoseLaborData;
    private ResponseTriGlyceridesDTO lastTriGlycerideLaborData;

}
