package com.greenfoxacademy.hta.services.nutrition;

import com.greenfoxacademy.hta.dtos.nutritiondto.*;
import com.greenfoxacademy.hta.exceptions.*;
import com.greenfoxacademy.hta.models.nutrition.FoodstuffType;
import com.greenfoxacademy.hta.models.nutrition.ReadyFoodType;
import com.greenfoxacademy.hta.models.nutrition.DynamicMeal;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface INutritionService {

   String addNewMeal(NewMealDTO newMealDTO, String email) throws MealNoFoodInItException, MealFoodstuffNotFoundException, UserNotFoundException, MealReadyFoodNotFoundException;

   String newFoodstuff(NewFoodStuffTypeDTO newFoodStuffTypeDTO, String email) throws FoodStuffIsAlreadyExistException;

   String newReadyFood(NewReadyFoodTypeDTO newReadyFoodTypeDTO, String email) throws ReadyFoodIsAlreadyExistException;
   List<FoodstuffType> getFoodstuffList();

   List<ReadyFoodType> getReadyFoodList();

   List<String> getReadyFoodTypeNames();

   GetAMealResponseDTO getAMeal(GetAMealDTO getAMealDTO, Authentication authentication)
           throws UserNotFoundException, MealDoesNotExistException;

   List<String> getFoodStuffTypeNames();

   void clearDynamicMeal();

   String registerDynamicMeal(NewMealDTO newMealDTO) throws MealNoFoodInItException,
           MealReadyFoodNotFoundException, MealFoodstuffNotFoundException;

   List<DynamicMeal> findAllDynamicMealData();
   MealSumAggregateDataDTO getLastMealData(Authentication authentication) throws MealDoesNotExistException;
}
