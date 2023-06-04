package com.greenfoxacademy.hta.repositories.nutrition;

import com.greenfoxacademy.hta.models.nutrition.ReadyFoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IReadyFoodTypeRepository extends JpaRepository<ReadyFoodType, Long> {
  Optional<ReadyFoodType> findByName(String name);
    boolean existsByName(String name);
}


