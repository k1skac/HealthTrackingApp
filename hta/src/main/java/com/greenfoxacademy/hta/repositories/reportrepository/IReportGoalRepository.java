package com.greenfoxacademy.hta.repositories.reportrepository;

import com.greenfoxacademy.hta.models.goalentities.Exercise;
import com.greenfoxacademy.hta.models.goalentities.Goal;
import com.greenfoxacademy.hta.models.goalentities.HealthyEating;
import com.greenfoxacademy.hta.models.goalentities.WeightLoss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IReportGoalRepository extends JpaRepository <Goal, Long> {

    @Query("SELECT goal FROM Goal goal " +
            "LEFT JOIN  goal.calorieIntakeLimit " +
            "LEFT JOIN goal.weightLoss " +
            "LEFT JOIN goal.exercise " +
            "WHERE (goal.user.id = ?1 AND goal.calorieIntakeLimit.deadline > ?2 ) " +
            "Or (goal.user.id = ?1 AND goal.weightLoss.deadline > ?2) " +
            "Or goal.user.id = ?1 AND goal.exercise.deadline > ?2"
    )
    List<Goal> getCurrentGoals(Long userId, Date currentDate);
    //https://stackoverflow.com/questions/12498949/jpql-multiple-left-join-with-one-class

    @Query("SELECT goal.weightLoss FROM Goal goal WHERE goal.user.id = ?1 AND goal.weightLoss.deadline > ?2 order by goal.weightLoss.deadline ASC")
    List<WeightLoss> getActiveWeightLostId(Long userId, Date currentDate);

    @Query("select goal.goalDescription From Goal goal WHERE goal.user.id = ?2 AND goal.weightLoss.deadline > ?1 OR " +
            "goal.user.id = ?2 AND goal.calorieIntakeLimit.deadline > ?1 ")
    Optional<List<Goal>> viewActiveGoal(Date currentDate, Long userId);

    @Query("SELECT goal.weightLoss FROM Goal goal WHERE goal.user.id = ?1 ORDER BY goal.weightLoss.deadline DESC LIMIT 1")
    Optional<WeightLoss> getLastWeightLostGoal(Long userId);

    @Query("SELECT goal.calorieIntakeLimit FROM Goal goal WHERE goal.user.id = ?1 ORDER BY goal.calorieIntakeLimit.deadline DESC LIMIT 1")
    Optional<HealthyEating> getLastHealthyEatingGoal(Long userId);

    @Query("SELECT goal.exercise FROM Goal goal WHERE goal.user.id = ?1 ORDER BY goal.exercise.deadline DESC LIMIT 1")
    Optional<Exercise> getLastExerciseGoal(Long userId);

    @Query("SELECT goal.exercise FROM Goal goal WHERE goal.user.id = ?1 AND goal.exercise.deadline > ?2 order by goal.exercise.deadline ASC")
    Optional<Exercise> getActiveExercise(Long userId, Date currentDate);
}
