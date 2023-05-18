package com.greenfoxacademy.hta.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BloodLabDataLimits {

    private final float systolicMax =140;
    private final float diastiolicMin =80;
    private final double maxGlucoseValue = 6.0;
    private final double minGlucoseValue = 3.0;
    private final double maxCalciumValue = 2.7;
    private final double minCalciumValue = 2.1;
    private final double maxTriGlyceridesValue = 1.7;
}
