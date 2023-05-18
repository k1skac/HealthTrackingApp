package com.greenfoxacademy.hta.repositories.reportrepository;

import com.greenfoxacademy.hta.models.healthylivingentities.ExerciseTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IReportExerciseTimeRepository extends JpaRepository<ExerciseTime, Long> {
    @Query("SELECT SUM(exerciseTime.burntCalorie), exerciseTime.user.id, exerciseTime.creationDate " +
            "FROM ExerciseTime exerciseTime WHERE exerciseTime.user.id = ?1" +
            " group by  exerciseTime.creationDate")
    List<ExerciseTime> dailyCalorieBurning (Long userId);

}
