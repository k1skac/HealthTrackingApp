package com.greenfoxacademy.hta.services.nutrition;


import com.greenfoxacademy.hta.dtos.nutritiondto.*;
import com.greenfoxacademy.hta.exceptions.*;
import com.greenfoxacademy.hta.models.nutrition.*;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.log.ILogRepository;
import com.greenfoxacademy.hta.repositories.log.ILogTypeRepository;
import com.greenfoxacademy.hta.repositories.nutrition.*;
import com.greenfoxacademy.hta.services.user.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class NutritionService implements INutrtionService {


  private final IFoodStuffRepository iFoodStuffRepository;
  private final IReadyFoodRepository iReadyFoodRepository;

  @Autowired
  public NutritionService(IFoodStuffTypeRepository iFoodStuffTypeRepository, IReadyFoodTypeRepository iReadyFoodTypeRepository,
                          IMealRepository iMealRepository, ILogRepository iLogRepository,
                          ILogTypeRepository iLogTypeRepository, IUserService iUserService, IFoodStuffRepository iFoodStuffRepository,
                          IReadyFoodRepository iReadyFoodRepository) {
    this.iFoodStuffTypeRepository = iFoodStuffTypeRepository;
    this.iReadyFoodTypeRepository = iReadyFoodTypeRepository;
    this.iMealRepository = iMealRepository;
    this.iLogRepository = iLogRepository;
    this.iLogTypeRepository = iLogTypeRepository;
    this.iUserService = iUserService;
    this.iFoodStuffRepository = iFoodStuffRepository;
    this.iReadyFoodRepository = iReadyFoodRepository;
  }

  IFoodStuffTypeRepository iFoodStuffTypeRepository;
  IReadyFoodTypeRepository iReadyFoodTypeRepository;
  IMealRepository iMealRepository;
  ILogRepository iLogRepository;
  ILogTypeRepository iLogTypeRepository;
  IUserService iUserService;

  public String addNewMeal(NewMealDTO newMealDTO, String email) throws MealNoFoodInItException,
           MealReadyFoodNotFoundException, MealFoodstuffNotFoundException {
    if (newMealDTO.getFoodstuffsList().isEmpty() && newMealDTO.getReadyFoodsList().isEmpty()) {
      throw new MealNoFoodInItException();
    }
    Meal newMeal = new Meal(newMealDTO.getMealTime());
    newMeal.setUser(iUserService.findByEmail(email));
    if (!newMealDTO.getFoodstuffsList().isEmpty()) {
       newMeal = fillFoodStuffList(newMeal, newMealDTO.getFoodstuffsList());
    }
    if (!newMealDTO.getReadyFoodsList().isEmpty()) {
      newMeal = fillReadyFoodList(newMeal, newMealDTO.getReadyFoodsList());
    }
    iMealRepository.save(newMeal);
    iFoodStuffRepository.saveAll(newMeal.getFoodstuffs());
    iReadyFoodRepository.saveAll(newMeal.getReadyFoods());
    iUserService.newLog(iLogTypeRepository.findLogTypeByName("newmeal"), iUserService.findByEmail(email),
            "The new meal time is " + newMeal.getMealTime());
    return "The meal was registered";
  }

  public Meal fillReadyFoodList(Meal newMeal, Map<String,Double> foodStuffList) throws MealReadyFoodNotFoundException {
    for (Map.Entry<String,Double> entry : foodStuffList.entrySet()) {
      try {
        ReadyFoodType readyFoodType = iReadyFoodTypeRepository.findByName(entry.getKey())
                .orElseThrow(() -> new MealReadyFoodNotFoundException(entry.getKey()));
        double quantity = entry.getValue();
        ReadyFood readyFood = new ReadyFood(quantity, newMeal, readyFoodType);
        newMeal.getReadyFoods().add(readyFood);
        newMeal.setMealSumCalorie(newMeal.getMealSumCalorie() + readyFoodType.getCaloriePerPortion() * quantity);
        newMeal.setMealSumFat(newMeal.getMealSumFat() + readyFoodType.getFatPerPortion() * quantity);
        newMeal.setMealSumCarbohydrate(newMeal.getMealSumCarbohydrate() + readyFoodType.getCarbohydratePerPortion() * quantity);
        newMeal.setMealSumProtein(newMeal.getMealSumProtein() + readyFoodType.getProteinPerPortion() * quantity);
      } catch (MealReadyFoodNotFoundException e) {
        throw new MealReadyFoodNotFoundException(entry.getKey());
      }
    }
    return newMeal;
  }

  public Meal fillFoodStuffList(Meal newMeal, Map<String,Double> readyFoodList) throws MealFoodstuffNotFoundException {
    for (Map.Entry<String,Double> entry : readyFoodList.entrySet()) {
      try {
        FoodstuffType foodStuffType = iFoodStuffTypeRepository.findByName(entry.getKey())
                .orElseThrow(() -> new MealFoodstuffNotFoundException(entry.getKey()));
        double quantity = entry.getValue();
        Foodstuff foodstuff = new Foodstuff(quantity, newMeal, foodStuffType);
        newMeal.getFoodstuffs().add(foodstuff);
        newMeal.setMealSumCalorie(newMeal.getMealSumCalorie() + foodStuffType.getCaloriePer100g() * quantity / 100);
        newMeal.setMealSumFat(newMeal.getMealSumFat() + foodStuffType.getFatPer100g() * quantity / 100);
        newMeal.setMealSumCarbohydrate(newMeal.getMealSumCarbohydrate() + foodStuffType.getCarbohydratePer100g() * quantity / 100);
        newMeal.setMealSumProtein(newMeal.getMealSumProtein() + foodStuffType.getProteinPer100g() * quantity / 100);
      } catch (MealFoodstuffNotFoundException e) {
        throw new MealFoodstuffNotFoundException(entry.getKey());
      }
    }
    return newMeal;
  }

  public String newFoodstuff(NewFoodStuffTypeDTO newFoodStuffTypeDTO, String email) throws FoodStuffIsAlreadyExistException {
     try {
       if (iFoodStuffTypeRepository.existsByName(newFoodStuffTypeDTO.getName())) {
         throw new FoodStuffIsAlreadyExistException();
       } else {
         iFoodStuffTypeRepository.save(new FoodstuffType(newFoodStuffTypeDTO.getName(), newFoodStuffTypeDTO.getCaloriePer100g(),
                 newFoodStuffTypeDTO.getFatPer100g(), newFoodStuffTypeDTO.getCarbohydratePer100g(),
                 newFoodStuffTypeDTO.getProteinPer100g()));
         iUserService.newLog(iLogTypeRepository.findLogTypeByName("newfoodstuff"), iUserService.findByEmail(email),
                         "The new foodstuff is " + newFoodStuffTypeDTO.getName());
         return "The " + newFoodStuffTypeDTO.getName() + " is registered";
       }
     } catch (FoodStuffIsAlreadyExistException e) {
       throw new FoodStuffIsAlreadyExistException();
     }
   }

  public String newReadyFood(NewReadyFoodTypeDTO newReadyFoodTypeDTO, String email) throws ReadyFoodIsAlreadyExistException {
    try {
      if (iReadyFoodTypeRepository.existsByName(newReadyFoodTypeDTO.getName())) {
        throw new ReadyFoodIsAlreadyExistException();
      } else {
        iReadyFoodTypeRepository.save(new ReadyFoodType(newReadyFoodTypeDTO.getName(), newReadyFoodTypeDTO.getCaloriePerPortion(),
                newReadyFoodTypeDTO.getFatPerPortion(), newReadyFoodTypeDTO.getCarbohydratePerPortion(),
                newReadyFoodTypeDTO.getProteinPerPortion()));
        iUserService.newLog(iLogTypeRepository.findLogTypeByName("newreadyfood"), iUserService.findByEmail(email),
                "The new foodstuff is " + newReadyFoodTypeDTO.getName());
        return "The " + newReadyFoodTypeDTO.getName() + " is registered";
      }
    } catch (ReadyFoodIsAlreadyExistException e) {
      throw new ReadyFoodIsAlreadyExistException();
    }
  }

  public List<FoodstuffType> getFoodstuffList() {
      return iFoodStuffTypeRepository.findAll();
  }

  public List<ReadyFoodType> getReadyFoodList() {
    return iReadyFoodTypeRepository.findAll();
  }

  @Override
  public List<String> getReadyFoodTypeNames() {
    List<String> readyFoodTypesString = new ArrayList<>();
    List<ReadyFoodType> readyFoodTypes = iReadyFoodTypeRepository.findAll();

    if (readyFoodTypes != null) {
      readyFoodTypesString = readyFoodTypes.stream()
              .map(ReadyFoodType::getName)
              .collect(Collectors.toList());
    }

    return readyFoodTypesString;
  }


  public GetAMealResponseDTO getAMeal(GetAMealDTO getAMealDTO, Authentication authentication)
          throws MealDoesNotExistException {
    try {
      User user = iUserService.findByEmail(getAMealDTO.getEmail());
      GetAMealResponseDTO response = new GetAMealResponseDTO(getAMealDTO.getEmail());
      response.setMeal(iMealRepository.findMealByMealTimeAndUser(getAMealDTO.getMealTime(), user).
              orElseThrow(MealDoesNotExistException::new));
      iUserService.newLog(iLogTypeRepository.findLogTypeByName("getameal"), iUserService.findByEmail(authentication.getName()),
              "Requested user: " + getAMealDTO.getEmail() + " requested mealtime : " + getAMealDTO.getMealTime());
      return response;
    } catch (MealDoesNotExistException e) {
      throw new MealDoesNotExistException();
    }
  }

  @Override
  public List<String> getFoodStuffTypeNames() {
    List<String> foodStuffTypesString = new ArrayList<>();
    List<FoodstuffType> foodstuffTypes = iFoodStuffTypeRepository.findAll();

    if (foodstuffTypes != null) {
      foodStuffTypesString = foodstuffTypes.stream()
              .map(FoodstuffType::getName)
              .collect(Collectors.toList());
    }
    return foodStuffTypesString;
  }
}