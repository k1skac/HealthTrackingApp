package com.greenfoxacademy.hta.models.nutrition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FoodstuffType implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    String name;
    double caloriePer100g;
    double fatPer100g;
    double carbohydratePer100g;
    double proteinPer100g;
    @JsonIgnore
    @OneToMany(mappedBy = "foodstuffType")
    List<Foodstuff> foodstuffs;

    public FoodstuffType( String name, double caloriePer100g, double fatPer100g, double carbohydratePer100g, double proteinPer100g) {
        this.name = name;
        this.caloriePer100g = caloriePer100g;
        this.fatPer100g = fatPer100g;
        this.carbohydratePer100g = carbohydratePer100g;
        this.proteinPer100g = proteinPer100g;
    }
}
