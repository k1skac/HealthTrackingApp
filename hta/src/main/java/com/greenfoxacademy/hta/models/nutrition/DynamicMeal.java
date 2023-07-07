package com.greenfoxacademy.hta.models.nutrition;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicMeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    double mealSumCalorie;
    double mealSumCarbohydrate;
    double mealSumFat;
    double mealSumProtein;
}
