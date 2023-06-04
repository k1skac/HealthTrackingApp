package com.greenfoxacademy.hta.repositories.nutrition;

import com.greenfoxacademy.hta.models.nutrition.Foodstuff;
import com.greenfoxacademy.hta.models.nutrition.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFoodStuffRepository extends JpaRepository<Foodstuff, Long> {

}


