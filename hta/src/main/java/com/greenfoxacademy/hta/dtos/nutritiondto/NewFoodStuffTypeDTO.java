package com.greenfoxacademy.hta.dtos.nutritiondto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NewFoodStuffTypeDTO {
    String name;
    float caloriePer100g;
    float fatPer100g;
    float carbohydratePer100g;
    float proteinPer100g;
}
