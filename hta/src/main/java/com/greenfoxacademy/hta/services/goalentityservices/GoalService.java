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
                    goal.getWeightLoss().getDeadline(),
                    goal.getWeightLoss().getStartDate());
            goalDto.setWeightLoss(weightLossDTO);
            HealthyEatingDTO healthyEatingDTO = new HealthyEatingDTO(
                    goal.getCalorieIntakeLimit().getDescription(),
                    goal.getCalorieIntakeLimit().getCalorieLimit(),
                    goal.getCalorieIntakeLimit().getDeadline(),
                    goal.getCalorieIntakeLimit().getDeadline());
            goalDto.setCalorieIntakeLimit(healthyEatingDTO);
            ExerciseDTO exerciseDTO = new ExerciseDTO(
                    goal.getExercise().getDescription(),
                    goal.getExercise().getDailyActiveTime().toMinutes(),
                    goal.getExercise().getDeadline(),
                    goal.getExercise().getStartDate());
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
                goalDTO.getWeightLoss().getDescription(),
                goalDTO.getWeightLoss().getGoalWeight(),
                goalDTO.getWeightLoss().getDeadline(),
                goalDTO.getWeightLoss().getStartDate());
        HealthyEating healthyEating = new HealthyEating(
                goalDTO.getCalorieIntakeLimit().getDescription(),
                goalDTO.getCalorieIntakeLimit().getCalorieLimit(),
                goalDTO.getCalorieIntakeLimit().getDeadline(),
                goalDTO.getCalorieIntakeLimit().getStartDate());
        Exercise exercise = new Exercise(
                goalDTO.getExercise().getDescription(),
                goalDTO.getExercise().getDailyActiveTimeInMinutes(),
                goalDTO.getExercise().getDeadline(),
                goalDTO.getExercise().getStartDate());
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
        Goal savedGoal = IGoalRepository.save(goal);

        user.getGoals().add(goal);
        iUserRepository.save(user);
        //ToDo LogSave
        return convertGoal(savedGoal);
    }

    @Override
    public GoalDTO updateGoal(Long id, GoalDTO goalDTO, Authentication authentication) throws HtaException {
        Goal goal = IGoalRepository.findGoalById(id);
        if (goal == null) {
            throw new GoalNotFoundException();
        }
        goal.setGoalDescription(goalDTO.getGoalDescription());
        goal.setWeightLoss(
                goalDTO.getWeightLoss().getDescription(),
                goalDTO.getWeightLoss().getGoalWeight(),
                goalDTO.getWeightLoss().getDeadline(),
                goalDTO.getWeightLoss().getStartDate());
        goal.setCalorieIntakeLimit(
                goalDTO.getCalorieIntakeLimit().getDescription(),
                goalDTO.getCalorieIntakeLimit().getCalorieLimit(),
                goalDTO.getCalorieIntakeLimit().getDeadline());
                goalDTO.getCalorieIntakeLimit().getStartDate();
        goal.setDailyActiveTime(
                goalDTO.getExercise().getDescription(),
                goalDTO.getExercise().getDailyActiveTimeInMinutes(),
                goalDTO.getExercise().getDeadline());
                goalDTO.getExercise().getStartDate();
        Goal updatedGoal = IGoalRepository.save(goal);
        //ToDo LogSave
        return convertGoal(updatedGoal);
    }

    @Override
    public GoalDTO convertGoal(Goal goal) {
        GoalDTO convertedGoalDTO = new GoalDTO();
        convertedGoalDTO.setGoalDescription(goal.getGoalDescription());
        convertedGoalDTO.setWeightLoss(new WeightLossDTO(
                goal.getWeightLoss().getDescription(),
                goal.getWeightLoss().getGoalWeight(),
                goal.getWeightLoss().getDeadline(),
                goal.getWeightLoss().getStartDate()));
        convertedGoalDTO.setCalorieIntakeLimit(new HealthyEatingDTO(
                goal.getCalorieIntakeLimit().getDescription(),
                goal.getCalorieIntakeLimit().getCalorieLimit(),
                goal.getCalorieIntakeLimit().getDeadline(),
                goal.getCalorieIntakeLimit().getStartDate()));
        convertedGoalDTO.setExercise(new ExerciseDTO(
                goal.getExercise().getDescription(),
                goal.getExercise().getDailyActiveTime().toMinutes(),
                goal.getExercise().getDeadline(),
                goal.getExercise().getStartDate()));
        return convertedGoalDTO;

    }

    @Override
    public Goal giveLastGoal() {
        return IGoalRepository.findLastGoal();
    }
}
