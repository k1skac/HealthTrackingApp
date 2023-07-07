package com.greenfoxacademy.hta.controllers;

import com.greenfoxacademy.hta.dtos.nutritiondto.*;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.exceptions.MealFoodstuffNotFoundException;
import com.greenfoxacademy.hta.exceptions.MealNoFoodInItException;
import com.greenfoxacademy.hta.exceptions.MealReadyFoodNotFoundException;
import com.greenfoxacademy.hta.models.nutrition.DynamicData;
import com.greenfoxacademy.hta.models.nutrition.DynamicMeal;
import com.greenfoxacademy.hta.services.nutrition.IDynamicDataService;
import com.greenfoxacademy.hta.services.nutrition.INutritionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/user/nutrition")
@RequiredArgsConstructor
public class NutritionRestController {

  private final INutritionService iNutritionService;
  private final IDynamicDataService iDynamicDataService;

  @PostMapping("/add-meal")
  public ResponseEntity<?> registerMeal (@RequestBody NewMealDTO newMealDTO, Authentication authentication) {
    try {
      return ResponseEntity.ok(iNutritionService.addNewMeal(newMealDTO, authentication.getName()));
    } catch (HtaException exception) {
      return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
  }

  @PostMapping("/add-foodStuff")
  public ResponseEntity<?> registerFoodStuff (@RequestBody NewFoodStuffTypeDTO newFoodStuffTypeDTO, Authentication authentication) {
    try {
      return ResponseEntity.ok(iNutritionService.newFoodstuff(newFoodStuffTypeDTO, authentication.getName()));
    } catch (HtaException exception) {
      return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
  }

  @PostMapping("/add-readyFood")
  public ResponseEntity<?> registerReadyFood (@RequestBody NewReadyFoodTypeDTO newReadyFoodTypeDTO, Authentication authentication) {
    try {
      return ResponseEntity.ok(iNutritionService.newReadyFood(newReadyFoodTypeDTO, authentication.getName()));
    } catch (HtaException exception) {
      return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
  }

  @GetMapping("/readyFoodTypes")
  public List<String> getReadyFoodTypesString () {
    return iNutritionService.getReadyFoodTypeNames();
  }

  @GetMapping("/food-stuff-types")
  public List<String> getFoodStuufTypesString() {
    return iNutritionService.getFoodStuffTypeNames();
  }

  @GetMapping("/get-foodstuff-list")
  public ResponseEntity<?> getFoodstuffList() throws HtaException {
    return ResponseEntity.ok(iNutritionService.getFoodstuffList());
  }

  @GetMapping("/get-a-meal")
  public ResponseEntity<?> getAMeal(@RequestBody GetAMealDTO getAMealDTO, Authentication authentication) throws HtaException {
    try {
      return ResponseEntity.ok(iNutritionService.getAMeal(getAMealDTO, authentication));
    } catch (HtaException exception) {
      return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
  }

  @PostMapping("/post-food-data")
  public ResponseEntity<?> saveDynamicData(@RequestBody DynamicData dynamicData) {
    iDynamicDataService.saveData(dynamicData);
    return ResponseEntity.ok("success");
  }

  @GetMapping("/get-food-data")
  public ResponseEntity<?> getDynamicData() {
    List<DynamicData> allData = iDynamicDataService.findAllData();
    if (!allData.isEmpty()) {
      int dynamicDataDoubled = 2 * allData.get(allData.size() - 1).getDynamicInt();
      return ResponseEntity.ok(dynamicDataDoubled);
    } else {
      return ResponseEntity.ok(0);
    }
  }

  @GetMapping("/nutrition-dynamic")
  public ResponseEntity<?> viewLastNutritionContent() {
    MealSumAggregateDataDTO myMeal = new MealSumAggregateDataDTO(100, 10, 40 ,40);
    return ResponseEntity.ok(myMeal);
  }

  @PostMapping("/nutrition-dynamic-save")
  public ResponseEntity<?> saveMealData(@RequestBody NewMealDTO newMealDTO) throws MealNoFoodInItException, MealFoodstuffNotFoundException, MealReadyFoodNotFoundException {
    iNutritionService.registerDynamicMeal(newMealDTO);
    return ResponseEntity.ok("ok");
  }

  @GetMapping("/nutrition-dynamic-load")
  public ResponseEntity<?> loadMealData() {
    MealSumAggregateDataDTO myMeal = new MealSumAggregateDataDTO();
    List<DynamicMeal> allData = iNutritionService.findAllDynamicMealData();
    if (!allData.isEmpty()) {
      DynamicMeal lastDynamicMeal = allData.get(allData.size()-1);
      myMeal.setMealSumCalorie(lastDynamicMeal.getMealSumCalorie());
      myMeal.setMealSumFat(lastDynamicMeal.getMealSumFat());
      myMeal.setMealSumCarbohydrate(lastDynamicMeal.getMealSumCarbohydrate());
      myMeal.setMealSumProtein(lastDynamicMeal.getMealSumProtein());
      iNutritionService.clearDynamicMeal();
      return ResponseEntity.ok(myMeal);
    } else {
      return ResponseEntity.ok(new MealSumAggregateDataDTO(0,0,0,0));
    }
  }
}
