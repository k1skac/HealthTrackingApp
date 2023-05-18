package com.greenfoxacademy.hta.repositories.reportrepository;

import com.greenfoxacademy.hta.models.goalentities.WeightLoss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IReportWeightLossRepository extends JpaRepository<WeightLoss, Long> {
    @Query("select weightLoss FROM WeightLoss weightLoss WHERE weightLoss.goal.user.id = ?1 And date(weightLoss.deadline) > current_date  ORDER BY weightLoss.deadline desc LIMIT 1")
    WeightLoss getLastExistingWeightLossGoal(Long userId);
}
