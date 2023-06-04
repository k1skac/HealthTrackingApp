package com.greenfoxacademy.hta.models.nutrition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Foodstuff implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    double quantity;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    Meal meal;

    @ManyToOne(fetch = FetchType.EAGER)
    FoodstuffType foodstuffType;

    public Foodstuff(double quantity, Meal meal, FoodstuffType foodstuffType) {
        this.quantity = quantity;
        this.meal = meal;
        this.foodstuffType = foodstuffType;
    }
}
