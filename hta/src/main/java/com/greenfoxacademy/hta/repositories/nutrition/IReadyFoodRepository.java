package com.greenfoxacademy.hta.repositories.nutrition;

import com.greenfoxacademy.hta.models.nutrition.Foodstuff;
import com.greenfoxacademy.hta.models.nutrition.ReadyFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReadyFoodRepository extends JpaRepository<ReadyFood, Long> {

}


