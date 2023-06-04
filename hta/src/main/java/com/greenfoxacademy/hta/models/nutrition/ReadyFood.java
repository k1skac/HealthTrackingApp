package com.greenfoxacademy.hta.models.nutrition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReadyFood implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    double quantity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    Meal meal;

    @ManyToOne(fetch = FetchType.EAGER)
    ReadyFoodType readyFoodType;

    public ReadyFood(double quantity, Meal meal, ReadyFoodType readyFoodType) {
        this.quantity = quantity;
        this.meal = meal;
        this.readyFoodType = readyFoodType;
    }
}
