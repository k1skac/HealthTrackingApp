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
public class ReadyFoodType implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    String name;
    double caloriePerPortion;
    double fatPerPortion;
    double carbohydratePerPortion;
    double proteinPerPortion;
    @JsonIgnore
    @OneToMany(mappedBy = "readyFoodType")
    List<ReadyFood> readyFoods;

    public ReadyFoodType(String name, double caloriePerPortion, double fatPerPortion, double carbohydratePerPortion, double proteinPerPortion) {
        this.name = name;
        this.caloriePerPortion = caloriePerPortion;
        this.fatPerPortion = fatPerPortion;
        this.carbohydratePerPortion = carbohydratePerPortion;
        this.proteinPerPortion = proteinPerPortion;
    }
}
