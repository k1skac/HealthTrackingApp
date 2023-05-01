package com.greenfoxacademy.hta.services.goalentityservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.hta.dtos.goalentitydto.*;
import com.greenfoxacademy.hta.exceptions.GoalNotFoundException;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.models.goalentities.Exercise;
import com.greenfoxacademy.hta.models.goalentities.Goal;
import com.greenfoxacademy.hta.models.goalentities.HealthyEating;
import com.greenfoxacademy.hta.models.goalentities.WeightLoss;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.repositories.goalentityrepositories.IExerciseRepository;
import com.greenfoxacademy.hta.repositories.goalentityrepositories.IGoalRepository;
import com.greenfoxacademy.hta.repositories.goalentityrepositories.IHealthyEatingRepository;
import com.greenfoxacademy.hta.repositories.goalentityrepositories.IWeightLossRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService implements IGoalService {
    private final IGoalRepository IGoalRepository;
    private final IWeightLossRepository IWeightLossRepository;
    private final IHealthyEatingRepository IHealthyEatingRepository;
    private final IExerciseRepository IExerciseRepository;
    private final IUserRepository iUserRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public List<GoalDTO> getAllByUser(Authentication authentication) {
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        List<Goal> goalList = IGoalRepository.findAllByUser(user);
        List<GoalDTO> goalDTOList = new ArrayList<>(goalList.size());
        return getGoalDTOS(goalList, goalDTOList);
    }

    private List<GoalDTO> getGoalDTOS(List<Goal> goalList, List<GoalDTO> goalDTOList) {
        for (Goal goal : goalList) {
            GoalDTO goalDto = new GoalDTO();
            goalDto.setGoalDescription(goal.getGoalDescription());
            WeightLossDTO weightLossDTO = new WeightLossDTO(
                    goal.getWeightLoss().getDescription(),
                    goal.getWeightLoss().getGoalWeight(),
                    goal.getWeightLoss().getDeadline());
            goalDto.setWeightLost(weightLossDTO);
            HealthyEatingDTO healthyEatingDTO = new HealthyEatingDTO(
                    goal.getCalorieIntakeLimit().getDescription(),
                    goal.getCalorieIntakeLimit().getCalorieLimit(),
                    goal.getCalorieIntakeLimit().getDeadline());
            goalDto.setCalorieIntakeLimit(healthyEatingDTO);
            ExerciseDTO exerciseDTO = new ExerciseDTO(
                    goal.getExercise().getDescription(),
                    goal.getExercise().getDailyActiveTime(),
                    goal.getExercise().getDeadline());
            goalDto.setExercise(exerciseDTO);
            goalDTOList.add(goalDto);
        }
        return goalDTOList;
    }

    @Override
    public List<GoalDTO> getAll(User user) {
        List<Goal> goalList = IGoalRepository.findAllByUser(user);
        List<GoalDTO> goalDTOList = new ArrayList<>();
        return getGoalDTOS(goalList, goalDTOList);
    }

    @Override
    public List<GoalDTO> deleteGoal(Long id, Authentication authentication) throws HtaException {
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        Goal goal = IGoalRepository.findGoalById(id);
        if (goal == null) {
            throw new GoalNotFoundException();
        }
        user.getGoals().remove(goal);
        IGoalRepository.delete(goal);
        iUserRepository.save(user);
        //ToDo LogSave
        return getAllByUser(authentication);
    }

    @Override
    public GoalDTO saveGoal(GoalDTO goalDTO, Authentication authentication) throws HtaException {
        if (goalDTO.getGoalDescription() == null || goalDTO.getGoalDescription().equals("")) {
            throw new HtaException("Goal description is needed.");
        }
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        Goal goal = new Goal(goalDTO.getGoalDescription());

        WeightLoss weightLoss = new WeightLoss(
                goalDTO.getWeightLost().getDescription(),
                goalDTO.getWeightLost().getGoalWeight(),
                goalDTO.getWeightLost().getDeadline());
        HealthyEating healthyEating = new HealthyEating(
                goalDTO.getCalorieIntakeLimit().getDescription(),
                goalDTO.getCalorieIntakeLimit().getCalorieLimit(),
                goalDTO.getCalorieIntakeLimit().getDeadline());
        Exercise exercise = new Exercise(
                goalDTO.getExercise().getDescription(),
                goalDTO.getExercise().getDailyActiveTime(),
                goalDTO.getExercise().getDeadline());
        weightLoss.setGoal(goal);
        healthyEating.setGoal(goal);
        exercise.setGoal(goal);
        IWeightLossRepository.save(weightLoss);
        IHealthyEatingRepository.save(healthyEating);
        IExerciseRepository.save(exercise);

        goal.setWeightLoss(weightLoss);
        goal.setCalorieIntakeLimit(healthyEating);
        goal.setExercise(exercise);
        goal.setUser(user);
        IGoalRepository.save(goal);

        user.getGoals().add(goal);
        iUserRepository.save(user);
        //ToDo LogSave
        return objectMapper.convertValue(goal, GoalDTO.class);
    }

    @Override
    public GoalDTO updateGoal(Long id, GoalDTO goalDTO, Authentication authentication) throws HtaException {
        Goal goal = IGoalRepository.findGoalById(id);
        if (goal == null) {
            throw new GoalNotFoundException();
        }
        goal.setGoalDescription(goalDTO.getGoalDescription());
        goal.setWeightLoss(
                goalDTO.getWeightLost().getDescription(),
                goalDTO.getWeightLost().getGoalWeight(),
                goalDTO.getWeightLost().getDeadline());
        goal.setCalorieIntakeLimit(
                goalDTO.getCalorieIntakeLimit().getDescription(),
                goalDTO.getCalorieIntakeLimit().getCalorieLimit(),
                goalDTO.getCalorieIntakeLimit().getDeadline());
        goal.setDailyActiveTime(
                goalDTO.getExercise().getDescription(),
                goalDTO.getExercise().getDailyActiveTime(),
                goalDTO.getExercise().getDeadline());
        IGoalRepository.save(goal);
        //ToDo LogSave
        return objectMapper.convertValue(goal, GoalDTO.class);
    }

    @Override
    public GoalDTO convertGoal(Goal goal) {
        return objectMapper.convertValue(goal, GoalDTO.class);
    }

    @Override
    public Goal giveLastGoal() {
        return IGoalRepository.findLastGoal();
    }
}
