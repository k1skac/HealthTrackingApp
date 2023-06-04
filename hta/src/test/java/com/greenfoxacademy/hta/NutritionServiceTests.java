package com.greenfoxacademy.hta;

import com.greenfoxacademy.hta.dtos.nutritiondto.NewMealDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.exceptions.MealFoodstuffNotFoundException;
import com.greenfoxacademy.hta.exceptions.MealReadyFoodNotFoundException;
import com.greenfoxacademy.hta.models.log.LogType;
import com.greenfoxacademy.hta.models.nutrition.FoodstuffType;
import com.greenfoxacademy.hta.models.nutrition.ReadyFoodType;
import com.greenfoxacademy.hta.models.user.BiologicalGender;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.log.ILogRepository;
import com.greenfoxacademy.hta.repositories.log.ILogTypeRepository;
import com.greenfoxacademy.hta.repositories.nutrition.*;
import com.greenfoxacademy.hta.services.nutrition.NutritionService;
import com.greenfoxacademy.hta.services.user.IUserService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class NutritionServiceTests {

    @Test
     void addMealOK() throws HtaException {
        var iFoodStuffTypeRepository = mock(IFoodStuffTypeRepository.class);
        var iReadyFoodTypeRepository = mock(IReadyFoodTypeRepository.class);
        var iMealRepository = mock(IMealRepository.class);
        var iLogTypeRepository = mock(ILogTypeRepository.class);
        var iFoodStuffRepository = mock(IFoodStuffRepository.class);
        var iReadyFoodRepository = mock(IReadyFoodRepository.class);
        var iLogRepository = mock(ILogRepository.class);
        var iUserService = mock(IUserService.class);

        NutritionService nutritionService = new NutritionService(iFoodStuffTypeRepository, iReadyFoodTypeRepository,
               iMealRepository, iLogRepository, iLogTypeRepository, iUserService, iFoodStuffRepository,
               iReadyFoodRepository);
        User user = new User("User1", "user1@gmail.com", "password1",
                "DummyUser1", BiologicalGender.MALE, LocalDate.of(1988, 1, 4), 198.5);
        when(iLogTypeRepository.findLogTypeByName("newmeal")).thenReturn(new LogType("newmeal", "A new meal has registred by "));
        when(iUserService.findByEmail("user1@gmail.com")).thenReturn(user);
        when(iFoodStuffTypeRepository.findByName("Avocado"))
                .thenReturn(Optional.of(new FoodstuffType("Avocado", 578, 14.2, 1, 2)));
        when(iReadyFoodTypeRepository.findByName("Burger King Whopper"))
                .thenReturn(Optional.of(new ReadyFoodType("Burger King Whopper", 588, 32.7,42.5, 27.8 )));
        when(iReadyFoodTypeRepository.findByName("Norbi Update1 Párizsis zsemle"))
               .thenReturn(Optional.of(new ReadyFoodType("Norbi Update1 Párizsis zsemle", 140, 6.7,6.4, 7.8)));
        NewMealDTO newMealDTO = new NewMealDTO(LocalDateTime.parse("2023-05-20T10:30:00"),
                                    new HashMap<>(Map.of("Avocado", 25.5)),
                                    new HashMap<>(Map.of("Norbi Update1 Párizsis zsemle", 1.5,
                                                      "Burger King Whopper", 1.5)));
        String expectedMessage = "The meal was registered";

        assertEquals(expectedMessage, nutritionService.addNewMeal(newMealDTO, user.getEmail()));
    }

   @Test
   void addMealFoodstuffNotExist() {
      var iFoodStuffTypeRepository = mock(IFoodStuffTypeRepository.class);
      var iReadyFoodTypeRepository = mock(IReadyFoodTypeRepository.class);
      var iMealRepository = mock(IMealRepository.class);
      var iLogTypeRepository = mock(ILogTypeRepository.class);
      var iFoodStuffRepository = mock(IFoodStuffRepository.class);
      var iReadyFoodRepository = mock(IReadyFoodRepository.class);
      var iLogRepository = mock(ILogRepository.class);
      var iUserService = mock(IUserService.class);

      NutritionService nutritionService = new NutritionService(iFoodStuffTypeRepository, iReadyFoodTypeRepository,
              iMealRepository, iLogRepository, iLogTypeRepository, iUserService, iFoodStuffRepository,
              iReadyFoodRepository);
      User user = new User("User1", "user1@gmail.com", "password1",
              "DummyUser1", BiologicalGender.MALE, LocalDate.of(1988, 1, 4), 198.5);
      when(iLogTypeRepository.findLogTypeByName("newmeal")).thenReturn(new LogType("newmeal", "A new meal has registred by "));
      when(iUserService.findByEmail("user1@gmail.com")).thenReturn(user);
      when(iReadyFoodTypeRepository.findByName("Burger King Whopper"))
              .thenReturn(Optional.of(new ReadyFoodType("Burger King Whopper", 588, 32.7,42.5, 27.8 )));
      when(iReadyFoodTypeRepository.findByName("Norbi Update1 Párizsis zsemle"))
              .thenReturn(Optional.of(new ReadyFoodType("Norbi Update1 Párizsis zsemle", 140, 6.7,6.4, 7.8)));
      NewMealDTO newMealDTO = new NewMealDTO(LocalDateTime.parse("2023-05-20T10:30:00"),
              new HashMap<>(Map.of("Apple", 25.5)),
              new HashMap<>(Map.of("Norbi Update1 Párizsis zsemle", 1.5,
                      "Burger King Whopper", 1.5)));
      Exception exception = assertThrows(MealFoodstuffNotFoundException.class, () -> {
         nutritionService.addNewMeal(newMealDTO, user.getEmail());
      });
      String expectedMessage2 = "The foodstuff is not in the database: Apple";
      String actualMessage = exception.getMessage();
      assertTrue(actualMessage.contains(expectedMessage2));
   }

   @Test
   void addMealReadyFoodNotExist() {
      var iFoodStuffTypeRepository = mock(IFoodStuffTypeRepository.class);
      var iReadyFoodTypeRepository = mock(IReadyFoodTypeRepository.class);
      var iMealRepository = mock(IMealRepository.class);
      var iLogTypeRepository = mock(ILogTypeRepository.class);
      var iFoodStuffRepository = mock(IFoodStuffRepository.class);
      var iReadyFoodRepository = mock(IReadyFoodRepository.class);
      var iLogRepository = mock(ILogRepository.class);
      var iUserService = mock(IUserService.class);

      NutritionService nutritionService = new NutritionService(iFoodStuffTypeRepository, iReadyFoodTypeRepository,
              iMealRepository, iLogRepository, iLogTypeRepository, iUserService, iFoodStuffRepository,
              iReadyFoodRepository);
      User user = new User("User1", "user1@gmail.com", "password1",
              "DummyUser1", BiologicalGender.MALE, LocalDate.of(1988, 1, 4), 198.5);
      when(iLogTypeRepository.findLogTypeByName("newmeal")).thenReturn(new LogType("newmeal", "A new meal has registred by "));
      when(iUserService.findByEmail("user1@gmail.com")).thenReturn(user);
      when(iFoodStuffTypeRepository.findByName("Avocado"))
              .thenReturn(Optional.of(new FoodstuffType("Avocado", 578, 14.2, 1, 2)));
      when(iReadyFoodTypeRepository.findByName("Norbi Update1 Párizsis zsemle"))
              .thenReturn(Optional.of(new ReadyFoodType("Norbi Update1 Párizsis zsemle", 140, 6.7,6.4, 7.8)));
      NewMealDTO newMealDTO = new NewMealDTO(LocalDateTime.parse("2023-05-20T10:30:00"),
              new HashMap<>(Map.of("Avocado", 25.5)),
              new HashMap<>(Map.of("Norbi Update1 Párizsis zsemle", 1.5,
                      "Junk Food", 1.5)));
      Exception exception = assertThrows(MealReadyFoodNotFoundException.class, () -> {
         nutritionService.addNewMeal(newMealDTO, user.getEmail());
      });
      String expectedMessage2 = "The ready food is not in the database: Junk Food";
      String actualMessage = exception.getMessage();
      assertTrue(actualMessage.contains(expectedMessage2));
   }
}
