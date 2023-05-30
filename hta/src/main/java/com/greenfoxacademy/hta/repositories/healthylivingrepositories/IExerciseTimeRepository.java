package com.greenfoxacademy.hta.repositories.healthylivingrepositories;

import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.models.healthylivingentities.ExerciseTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExerciseTimeRepository extends JpaRepository<ExerciseTime, Long> {
    List<ExerciseTime> findAllByUser(User user);

    ExerciseTime findExerciseTimeById(Long id);

}
