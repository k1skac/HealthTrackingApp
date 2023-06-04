package com.greenfoxacademy.hta.dtos.nutritiondto;

import com.greenfoxacademy.hta.models.nutrition.Meal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetAMealResponseDTO {
    String userName;
    Meal meal;

    public GetAMealResponseDTO(String userName) {
        this.userName = userName;
    }
}