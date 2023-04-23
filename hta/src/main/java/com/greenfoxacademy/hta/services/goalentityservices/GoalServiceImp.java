package com.greenfoxacademy.hta.services.goalentityservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.hta.dtos.goalentitydto.*;
import com.greenfoxacademy.hta.exceptions.GoalNotFoundException;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.models.goalentities.Exercise;
import com.greenfoxacademy.hta.models.goalentities.Goal;
import com.greenfoxacademy.hta.models.goalentities.HealthyEating;
import com.greenfoxacademy.hta.models.goalentities.WeightLost;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.repositories.goalentityrepositories.IExerciseRepository;
import com.greenfoxacademy.hta.repositories.goalentityrepositories.IGoalRepository;
import com.greenfoxacademy.hta.repositories.goalentityrepositories.IHealthyEatingRepository;
import com.greenfoxacademy.hta.repositories.goalentityrepositories.IWeightLostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalServiceImp implements GoalService {
    private final IGoalRepository IGoalRepository;
    private final IWeightLostRepository IWeightLostRepository;
    private final IHealthyEatingRepository IHealthyEatingRepository;
    private final IExerciseRepository IExerciseRepository;
    private final IUserRepository iUserRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public List<GoalDTO> getAllByUser(Authentication authentication) {
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        List<Goal> goalList = IGoalRepository.findAllByUser(user);
        List<GoalDTO> goalDTOList = new ArrayList<>(goalList.size());
            for (Goal goal : goalList) {
                GoalDTO goalDto = new GoalDTO();
                goalDto.setGoalDescription(goal.getGoalDescription());
                WeightLostDTO weightLostDTO = new WeightLostDTO(
                        goal.getWeightLost().getDescription(),
                        goal.getWeightLost().getGoalWeight(),
                        goal.getWeightLost().getDeadline());
                goalDto.setWeightLost(weightLostDTO);
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
        for (Goal goal : goalList) {
            GoalDTO goalDto = new GoalDTO();
            goalDto.setGoalDescription(goal.getGoalDescription());
            WeightLostDTO weightLostDTO = new WeightLostDTO(
                    goal.getWeightLost().getDescription(),
                    goal.getWeightLost().getGoalWeight(),
                    goal.getWeightLost().getDeadline());
            goalDto.setWeightLost(weightLostDTO);
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
    public List<GoalDTO> deleteGoal(Long id, Authentication authentication) throws HtaException {
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        Goal goal = IGoalRepository.findGoalById(id);
        if (goal == null || goal.equals("")) {
            throw new GoalNotFoundException();
        }
        user.getGoals().remove(goal);
        IGoalRepository.delete(goal);
        iUserRepository.save(user);
        //ToDo LogSave
        //With debug, at healthyEatingRepository.delete(healthyEating) it wrote:null passed when non null expected.
        //This created a loop, and fives back forbidden. But it deletes the goals and related object.How to handle it?
        return getAllByUser(authentication);
    }

    @Override
    public GoalDTO saveGoal(GoalDTO goalDTO, Authentication authentication) throws HtaException {
        if (goalDTO.getGoalDescription() == null || goalDTO.getGoalDescription().equals("")) {
            throw new HtaException("Goal description is needed.");
        }
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        Goal goal = new Goal(goalDTO.getGoalDescription());

        WeightLost weightLost = new WeightLost(
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
        weightLost.setGoal(goal);
        healthyEating.setGoal(goal);
        exercise.setGoal(goal);
        IWeightLostRepository.save(weightLost);
        IHealthyEatingRepository.save(healthyEating);
        IExerciseRepository.save(exercise);

        goal.setWeightLost(weightLost);
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
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        Goal goal = IGoalRepository.findGoalById(id);
        if (goal == null || goal.equals("")) {
            throw new GoalNotFoundException();
        }
        goal.setGoalDescription(goalDTO.getGoalDescription());
        goal.setWeightLost(
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
