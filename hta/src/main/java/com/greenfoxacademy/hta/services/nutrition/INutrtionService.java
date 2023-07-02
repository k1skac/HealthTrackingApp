package com.greenfoxacademy.hta.services.nutrition;

import com.greenfoxacademy.hta.dtos.nutritiondto.*;
import com.greenfoxacademy.hta.exceptions.*;
import com.greenfoxacademy.hta.models.nutrition.FoodstuffType;
import com.greenfoxacademy.hta.models.nutrition.ReadyFoodType;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface INutrtionService {

   String addNewMeal(NewMealDTO newMealDTO, String email) throws MealNoFoodInItException, MealFoodstuffNotFoundException, UserNotFoundException, MealReadyFoodNotFoundException;

   String newFoodstuff(NewFoodStuffTypeDTO newFoodStuffTypeDTO, String email) throws FoodStuffIsAlreadyExistException;

   String newReadyFood(NewReadyFoodTypeDTO newReadyFoodTypeDTO, String email) throws ReadyFoodIsAlreadyExistException;
   List<FoodstuffType> getFoodstuffList();

   List<ReadyFoodType> getReadyFoodList();

   List<String> getReadyFoodTypeNames();

   GetAMealResponseDTO getAMeal(GetAMealDTO getAMealDTO, Authentication authentication)
           throws UserNotFoundException, MealDoesNotExistException;

   MealSumAggregateDataDTO getLastMealData(Authentication authentication) throws MealDoesNotExistException;

   List<String> getFoodStuffTypeNames();


}
