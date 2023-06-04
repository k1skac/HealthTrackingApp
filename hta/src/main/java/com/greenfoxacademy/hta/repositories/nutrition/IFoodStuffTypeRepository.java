package com.greenfoxacademy.hta.repositories.nutrition;

import com.greenfoxacademy.hta.models.nutrition.FoodstuffType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFoodStuffTypeRepository extends JpaRepository<FoodstuffType, Long> {
  Optional<FoodstuffType> findByName(String name);

    boolean existsByName(String name);

}


