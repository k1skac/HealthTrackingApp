package com.greenfoxacademy.hta.dtos.nutritiondto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NewReadyFoodTypeDTO implements Serializable  {
    String name;
    float caloriePerPortion;
    float fatPerPortion;
    float carbohydratePerPortion;
    float proteinPerPortion;


}
