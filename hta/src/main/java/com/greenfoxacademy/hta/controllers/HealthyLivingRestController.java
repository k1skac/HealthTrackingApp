package com.greenfoxacademy.hta.controllers;

import com.greenfoxacademy.hta.dtos.healthylivingentitydto.DailyCalorieIntakeDTO;
import com.greenfoxacademy.hta.dtos.healthylivingentitydto.ExerciseTimeDTO;
import com.greenfoxacademy.hta.dtos.saveuserdatadto.SaveWeightDTO;
import com.greenfoxacademy.hta.exceptions.*;
import com.greenfoxacademy.hta.services.healthylivingservices.DailyCalorieIntakeService;
import com.greenfoxacademy.hta.services.healthylivingservices.ExerciseTimeService;
import com.greenfoxacademy.hta.services.healthylivingservices.WeightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/healthy-living")
@RequiredArgsConstructor
public class HealthyLivingRestController {
    private final DailyCalorieIntakeService calorieIntakeServiceImp;
    private final ExerciseTimeService exerciseTimeService;
    private final WeightService weightService;

    @PostMapping("/calorie-intake")
    public ResponseEntity<?> newCalorie(@RequestBody DailyCalorieIntakeDTO dailyCalorieIntakeDTO, Authentication authentication) {
        try {
            return ResponseEntity.ok(calorieIntakeServiceImp.save(dailyCalorieIntakeDTO, authentication));
        } catch (DailyCalorieIntakeNoContentException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @PostMapping("/exercise-time")
    public ResponseEntity<?> newExercise(@RequestBody ExerciseTimeDTO exerciseTimeDTO, Authentication authentication) {
        try {
            return ResponseEntity.ok(exerciseTimeService.save(exerciseTimeDTO, authentication));
        } catch (ExerciseTimeNoContentException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }
    @PutMapping("/calorie-intake/{id}")
    public ResponseEntity<?> editCalorie(@PathVariable(name = "id") Long id, @RequestBody DailyCalorieIntakeDTO dailyCalorieIntakeDTO,
                                         Authentication authentication) {
        try {
            return ResponseEntity.ok(calorieIntakeServiceImp.edit(id, dailyCalorieIntakeDTO, authentication));
        } catch (DailyCalorieIntakeNotFoundException | DailyCalorieIntakeNoContentException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
    @PutMapping("/exercise-time/{id}")
    public ResponseEntity<?> editExerciseTime(@PathVariable(name = "id") Long id, @RequestBody ExerciseTimeDTO exerciseTimeDTO,
                                              Authentication authentication) {
        try {
            return ResponseEntity.ok(exerciseTimeService.edit(id, exerciseTimeDTO, authentication));
        } catch (ExerciseTimeNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
    @GetMapping("/calorie-intake")
    public ResponseEntity<List<DailyCalorieIntakeDTO>> getAllCalories(Authentication authentication) {
        return ResponseEntity.ok(calorieIntakeServiceImp.getAllByAuthentication(authentication));
    }
    @GetMapping("/exercise-time")
    public ResponseEntity<List<ExerciseTimeDTO>> getAllExercise(Authentication authentication) {
        return ResponseEntity.ok(exerciseTimeService.getAllByAuthentication(authentication));
    }
    @DeleteMapping("/calorie-intake/{id}")
    public ResponseEntity<?> deleteCalorie (@PathVariable(name = "id") Long id, Authentication authentication) {
        try {
            return ResponseEntity.ok(calorieIntakeServiceImp.delete(id,authentication));
        } catch (DailyCalorieIntakeNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
    @DeleteMapping("/exercise-time/{id}")
    public ResponseEntity<?> deleteExerciseTime (@PathVariable(name = "id") Long id, Authentication authentication) {
        try {
            return ResponseEntity.ok(exerciseTimeService.delete(id,authentication));
        } catch (ExerciseTimeNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/weight")
    public ResponseEntity<?> showAll(Authentication authentication) {
        return ResponseEntity.ok(weightService.getAll(authentication));
    }

    @PutMapping("/weight/{id}")
    public ResponseEntity<?> updateWeight(@PathVariable(name = "id") Long id, Authentication authentication,
                                          @RequestBody SaveWeightDTO saveWeightDTO) {
        try {
            return ResponseEntity.ok(weightService.edit(id, authentication, saveWeightDTO));
        } catch (WeightNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/weight/{id}")
    public ResponseEntity<?> deleteWeight(@PathVariable(name = "id") Long id, Authentication authentication) {
        try {
            return ResponseEntity.ok(weightService.delete(id, authentication));
        } catch (WeightNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}