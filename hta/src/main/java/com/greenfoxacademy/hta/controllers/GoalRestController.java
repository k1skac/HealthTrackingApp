package com.greenfoxacademy.hta.controllers;

import com.greenfoxacademy.hta.dtos.goalentitydto.*;
import com.greenfoxacademy.hta.exceptions.GoalNotFoundException;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.services.goalentityservices.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/goal")
@RequiredArgsConstructor
public class GoalRestController {
    private final GoalService goalService;
    @PostMapping("/")
    public ResponseEntity<?> newGoal(@RequestBody(required = false) GoalDTO goalDTO,
                                     Authentication authentication) throws HtaException {
        try {
            GoalDTO newGoalDto = goalService.saveGoal(goalDTO, authentication);
            return ResponseEntity.ok(newGoalDto);
        } catch (HtaException exception) {
            // status can't have parameter exception.getStatus!
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGoal(@PathVariable(name = "id") Long id,
                                        @RequestBody GoalDTO goalDTO,
                                        Authentication authentication) throws HtaException {
        try {
            GoalDTO newGoalDto = goalService.updateGoal(id, goalDTO, authentication);
            return ResponseEntity.ok(newGoalDto);
        } catch (GoalNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    // Still Needs Testing Manually. ObjectMapping with List may be the problem(Cant map the subclass in Goal?)
    public ResponseEntity<?> deleteGoal(@PathVariable(name = "id") Long id,
                                        Authentication authentication) throws HtaException {
        try {
            List<GoalDTO> goalDTOList = goalService.deleteGoal(id, authentication);
            return ResponseEntity.ok(goalDTOList);
        } catch (GoalNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

    }

    @GetMapping("/")
    // Still Needs Testing Manually. ObjectMapping with List may be the problem(Cant map the subclass in Goal?)
    public ResponseEntity<?> goalList(Authentication authentication) {
        List<GoalDTO> goalDTOList = goalService.getAllByUser(authentication);
        return ResponseEntity.ok(goalDTOList);
    }
}