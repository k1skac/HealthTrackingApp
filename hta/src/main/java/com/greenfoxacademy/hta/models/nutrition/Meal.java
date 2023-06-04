package com.greenfoxacademy.hta.models.nutrition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.hta.models.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Meal {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id ;
  LocalDateTime mealTime;
  @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Foodstuff> foodstuffs = new ArrayList<>();
  @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
  List<ReadyFood> readyFoods = new ArrayList<>();
  double mealSumCalorie;
  double mealSumCarbohydrate;
  double mealSumFat;
  double mealSumProtein;
  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER)
  User user;

  public Meal(LocalDateTime mealTime) {
    this.mealTime = mealTime;
  }
}
