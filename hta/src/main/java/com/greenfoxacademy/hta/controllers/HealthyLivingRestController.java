package com.greenfoxacademy.hta.controllers;

import com.greenfoxacademy.hta.dtos.healthylivingentitydto.DailyCalorieIntakeDTO;
import com.greenfoxacademy.hta.dtos.healthylivingentitydto.ExerciseTimeDTO;
import com.greenfoxacademy.hta.exceptions.DailyCalorieIntakeNoContentException;
import com.greenfoxacademy.hta.exceptions.DailyCalorieIntakeNotFoundException;
import com.greenfoxacademy.hta.exceptions.ExerciseTimeNoContentException;
import com.greenfoxacademy.hta.exceptions.ExerciseTimeNotFoundException;
import com.greenfoxacademy.hta.services.healthylivingservices.DailyCalorieIntakeServiceImp;
import com.greenfoxacademy.hta.services.healthylivingservices.ExerciseTimeServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/healthyliving")
@RequiredArgsConstructor
public class HealthyLivingRestController {
    private final DailyCalorieIntakeServiceImp calorieIntakeServiceImp;
    private final ExerciseTimeServiceImp exerciseTimeServiceImp;

    @PostMapping("/calorieintake")
    public ResponseEntity<?> newCalorie(@RequestBody DailyCalorieIntakeDTO dailyCalorieIntakeDTO, Authentication authentication) {
        try {
            DailyCalorieIntakeDTO dailyCalorieDTO = calorieIntakeServiceImp.save(dailyCalorieIntakeDTO, authentication);
            return ResponseEntity.ok(dailyCalorieDTO);
        } catch (DailyCalorieIntakeNoContentException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @PostMapping("/exercisetime")
    public ResponseEntity<?> newExercise(@RequestBody ExerciseTimeDTO exerciseTimeDTO, Authentication authentication) {
        try {
            ExerciseTimeDTO exerciseTimeDTO1 = exerciseTimeServiceImp.save(exerciseTimeDTO, authentication);
            return ResponseEntity.ok(exerciseTimeDTO1);
        } catch (ExerciseTimeNoContentException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }
    @PutMapping("/calorieintake/{id}")
    public ResponseEntity<?> editCalorie(@PathVariable(name = "id") Long id, @RequestBody DailyCalorieIntakeDTO dailyCalorieIntakeDTO, Authentication authentication) {
        try {
            List<DailyCalorieIntakeDTO> calorieIntakeDTOS = calorieIntakeServiceImp.edit(id, dailyCalorieIntakeDTO, authentication);
            return ResponseEntity.ok(calorieIntakeDTOS);
        } catch (DailyCalorieIntakeNotFoundException | DailyCalorieIntakeNoContentException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
    @PutMapping("/exercisetime/{id}")
    public ResponseEntity<?> editExerciseTime(@PathVariable(name = "id") Long id, @RequestBody ExerciseTimeDTO exerciseTimeDTO, Authentication authentication) {
        try {
            List<ExerciseTimeDTO> exerciseTimeDTOS = exerciseTimeServiceImp.edit(id, exerciseTimeDTO, authentication);
            return ResponseEntity.ok(exerciseTimeDTOS);
        } catch (ExerciseTimeNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
    @GetMapping("/calorieintake")
    public ResponseEntity<List<DailyCalorieIntakeDTO>> getAllCalories(Authentication authentication) {
        List<DailyCalorieIntakeDTO> calorieIntakeDTOS = calorieIntakeServiceImp.getAllByAuthentication(authentication);
        return ResponseEntity.ok(calorieIntakeDTOS);
    }
    @GetMapping("/exercisetime")
    public ResponseEntity<List<ExerciseTimeDTO>> getAllExercise(Authentication authentication) {
        List<ExerciseTimeDTO> exerciseTimeDTOS = exerciseTimeServiceImp.getAllbyAuthentication(authentication);
        return ResponseEntity.ok(exerciseTimeDTOS);
    }
    @DeleteMapping("/calorieintake/{id}")
    public ResponseEntity<?> deleteCalorie (@PathVariable(name = "id") Long id, Authentication authentication) {
        try {
            List<DailyCalorieIntakeDTO> calorieIntakeDTOS = calorieIntakeServiceImp.delete(id,authentication);
            return ResponseEntity.ok(calorieIntakeDTOS);
        } catch (DailyCalorieIntakeNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
    @DeleteMapping("/exercisetime/{id}")
    public ResponseEntity<?> deleteExerciseTime (@PathVariable(name = "id") Long id, Authentication authentication) {
        try {
            List<ExerciseTimeDTO> exerciseTimeDTOS = exerciseTimeServiceImp.delete(id,authentication);
            return ResponseEntity.ok(exerciseTimeDTOS);
        } catch (ExerciseTimeNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
