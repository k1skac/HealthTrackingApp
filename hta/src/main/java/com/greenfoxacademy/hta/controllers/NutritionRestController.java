package com.greenfoxacademy.hta.controllers;

import com.greenfoxacademy.hta.dtos.nutritiondto.GetAMealDTO;
import com.greenfoxacademy.hta.dtos.nutritiondto.NewFoodStuffTypeDTO;
import com.greenfoxacademy.hta.dtos.nutritiondto.NewMealDTO;
import com.greenfoxacademy.hta.dtos.nutritiondto.NewReadyFoodTypeDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.services.nutrition.INutrtionService;
import com.greenfoxacademy.hta.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/nutrition")
@RequiredArgsConstructor
public class NutritionRestController {


  private final INutrtionService iNutrtionService;

  @PostMapping("/add-meal")
  public ResponseEntity<?> registerMeal (@RequestBody NewMealDTO newMealDTO, Authentication authentication) {
    try {
      return ResponseEntity.ok(iNutrtionService.addNewMeal(newMealDTO, authentication.getName()));
    } catch (HtaException exception) {
      return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
  }

  @PostMapping("/add-foodStuff")
  public ResponseEntity<?> registerFoodStuff (@RequestBody NewFoodStuffTypeDTO newFoodStuffTypeDTO, Authentication authentication) {
    try {
      return ResponseEntity.ok(iNutrtionService.newFoodstuff(newFoodStuffTypeDTO, authentication.getName()));
    } catch (HtaException exception) {
      return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
  }

  @PostMapping("/add-readyFood")
  public ResponseEntity<?> registerReadyFood (@RequestBody NewReadyFoodTypeDTO newReadyFoodTypeDTO, Authentication authentication) {
    try {
      return ResponseEntity.ok(iNutrtionService.newReadyFood(newReadyFoodTypeDTO, authentication.getName()));
    } catch (HtaException exception) {
      return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
  }


  @GetMapping("/get-foodstuff-list")
  public ResponseEntity<?> getFoodstuffList() throws HtaException {
    return ResponseEntity.ok(iNutrtionService.getFoodstuffList());
  }

  @GetMapping("/get-a-meal")
  public ResponseEntity<?> getAMeal(@RequestBody GetAMealDTO getAMealDTO, Authentication authentication) throws HtaException {
    try {
      return ResponseEntity.ok(iNutrtionService.getAMeal(getAMealDTO, authentication));
    } catch (HtaException exception) {
      return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
  }
}
