package com.greenfoxacademy.hta.repositories.nutrition;

import com.greenfoxacademy.hta.models.nutrition.Meal;
import com.greenfoxacademy.hta.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface IMealRepository extends JpaRepository<Meal, Long> {
  Optional<Meal> findByUser(User user);

    Optional<Meal>  findMealByMealTimeAndUser(LocalDateTime mealTime, User user);
    Optional<Meal> findTop1MealByUserOrderByMealTimeDesc(User user);

}


