package com.greenfoxacademy.hta.dtos.nutritiondto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MealSumAggregateDataDTO {
    double mealSumCalorie;
    double mealSumCarbohydrate;
    double mealSumFat;
    double mealSumProtein;
}

