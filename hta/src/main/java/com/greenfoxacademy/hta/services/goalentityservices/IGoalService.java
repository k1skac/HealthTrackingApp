package com.greenfoxacademy.hta.services.goalentityservices;

import com.greenfoxacademy.hta.dtos.goalentitydto.*;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.models.goalentities.Goal;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IGoalService {
    List<GoalDTO> getAllByUser(Authentication authentication);
    List<GoalDTO> getAll(User user);

    List<GoalDTO> deleteGoal(Long id, Authentication authentication) throws HtaException;

    GoalDTO saveGoal(GoalDTO goalDTO, Authentication authentication) throws HtaException;

    GoalDTO updateGoal(Long id, GoalDTO goalDTO, Authentication authentication) throws HtaException;

    GoalDTO convertGoal(Goal goal);
    GoalDTO lastGoal (Authentication authentication) throws HtaException;
}
