package com.greenfoxacademy.hta.controllers;

import com.greenfoxacademy.hta.dtos.goalentitydto.*;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.services.goalentityservices.IGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/goal")
@RequiredArgsConstructor
public class GoalRestController {
    private final IGoalService IGoalService;
    @PostMapping("/")
    public ResponseEntity<?> newGoal(@RequestBody(required = false) GoalDTO goalDTO, Authentication authentication) {
        try {
            return ResponseEntity.ok(IGoalService.saveGoal(goalDTO, authentication));
        } catch (HtaException exception) {
            return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGoal(@PathVariable(name = "id") Long id,
                                        @RequestBody GoalDTO goalDTO,
                                        Authentication authentication) {
        try {
            return ResponseEntity.ok(IGoalService.updateGoal(id, goalDTO, authentication));
        } catch (HtaException exception) {
            return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoal(@PathVariable(name = "id") Long id,
                                        Authentication authentication) {
        try {
            return ResponseEntity.ok(IGoalService.deleteGoal(id, authentication));
        } catch (HtaException exception) {
            return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> goalList(Authentication authentication) {
        return ResponseEntity.ok(IGoalService.getAllByUser(authentication));
    }
    @GetMapping("/last-goal")
    public ResponseEntity<?> lastGoal( Authentication authentication) {
        try {
            return ResponseEntity.ok(IGoalService.lastGoal(authentication));
        } catch (HtaException exception) {
            return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
        }
    }

}