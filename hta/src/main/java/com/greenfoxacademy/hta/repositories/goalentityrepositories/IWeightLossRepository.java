package com.greenfoxacademy.hta.repositories.goalentityrepositories;

import com.greenfoxacademy.hta.models.goalentities.WeightLoss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWeightLossRepository extends JpaRepository<WeightLoss, Long> {
}
