package com.greenfoxacademy.hta.repositories.healthylivingrepositories;

import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.models.healthylivingentities.DailyCalorieIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDailyCalorieIntakeRepository extends JpaRepository <DailyCalorieIntake, Long> {
    List<DailyCalorieIntake> findAllByUser(User user);
    DailyCalorieIntake findDailyCalorieIntakeById(Long id);
}
