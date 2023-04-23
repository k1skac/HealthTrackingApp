package com.greenfoxacademy.hta.repositories.goalentityrepositories;

import com.greenfoxacademy.hta.models.goalentities.Goal;
import com.greenfoxacademy.hta.models.goalentities.WeightLost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWeightLostRepository extends JpaRepository<WeightLost, Long> {
    WeightLost findWeightLostByGoal (Goal goal);
}
