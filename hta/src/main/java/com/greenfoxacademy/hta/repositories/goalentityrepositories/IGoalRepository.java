package com.greenfoxacademy.hta.repositories.goalentityrepositories;

import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.models.goalentities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGoalRepository extends JpaRepository<Goal, Long> {
    boolean existsById(Long id);
    Goal findGoalById(Long id);
    Goal findGoalByIdAndUser (Long id, User user);
    @Query ("SELECT g FROM Goal g WHERE g.id = (SELECT MAX (g.id) FROM Goal g)")
    Goal findLastGoal();
    List<Goal> findAllByUser(User user);

}
