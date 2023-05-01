package com.greenfoxacademy.hta.repositories.goalentityrepositories;

import com.greenfoxacademy.hta.models.goalentities.Exercise;
import com.greenfoxacademy.hta.models.goalentities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExerciseRepository extends JpaRepository <Exercise, Long> {
}