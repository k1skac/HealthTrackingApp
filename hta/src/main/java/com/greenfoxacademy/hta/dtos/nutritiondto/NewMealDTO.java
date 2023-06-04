package com.greenfoxacademy.hta.dtos.nutritiondto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewMealDTO {
    LocalDateTime mealTime;
    HashMap<String, Double> foodstuffsList;
    HashMap<String, Double>  readyFoodsList;


}