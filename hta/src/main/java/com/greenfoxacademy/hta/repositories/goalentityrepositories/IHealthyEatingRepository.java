package com.greenfoxacademy.hta.repositories.goalentityrepositories;

import com.greenfoxacademy.hta.models.goalentities.HealthyEating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHealthyEatingRepository extends JpaRepository<HealthyEating, Long> {
}
