package com.greenfoxacademy.hta;

import com.greenfoxacademy.hta.dtos.healthylivingentitydto.DailyCalorieIntakeDTO;
import com.greenfoxacademy.hta.exceptions.DailyCalorieIntakeNoContentException;
import com.greenfoxacademy.hta.exceptions.DailyCalorieIntakeNotFoundException;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.models.healthylivingentities.DailyCalorieIntake;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.services.healthylivingservices.DailyCalorieIntakeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import java.util.List;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;


public class DailyCalorieIntakeServiceTesting {

    @Test
    public void whenEditingDailyCalorieIntake() throws DailyCalorieIntakeNotFoundException, DailyCalorieIntakeNoContentException {
        var iUserRepository = mock(IUserRepository.class);
        var authentication = mock(Authentication.class);
        var iDailyCalorieIntakeRepository =mock(com.greenfoxacademy.hta.repositories.healthylivingrepositories.IDailyCalorieIntakeRepository.class);
        var dailyCalorieIntakeService = new DailyCalorieIntakeService(iUserRepository,iDailyCalorieIntakeRepository);
        User user = new User();
        user.setEmail("mockyMcMockness@mockmail.mock");
        Optional<User> optional = Optional.of(user);

        when(authentication.getName()).thenReturn("user");
        when(iUserRepository.findByEmail("user")).thenReturn(optional);

        Long id = 1L;
        DailyCalorieIntake dailyCalorieIntake = new DailyCalorieIntake(id, 5000);
        when(iDailyCalorieIntakeRepository.findDailyCalorieIntakeById(id)).thenReturn(dailyCalorieIntake);
        when(authentication.getName()).thenReturn("user");
        DailyCalorieIntakeDTO dailyCalorieIntakeDTO = new DailyCalorieIntakeDTO(3000);
        dailyCalorieIntakeService.edit(id, dailyCalorieIntakeDTO, authentication);
        DailyCalorieIntake editedDailyCalorieIntake = iDailyCalorieIntakeRepository.findDailyCalorieIntakeById(id);
        Assertions.assertEquals(editedDailyCalorieIntake.getCalorie(), dailyCalorieIntakeDTO.getCalorie());
    }

    @Test
    public void exceptionWhenEditDailyCalorieIntakeNotFound() {
        var iUserRepository = mock(IUserRepository.class);
        var authentication = mock(Authentication.class);
        var iDailyCalorieIntakeRepository =mock(com.greenfoxacademy.hta.repositories.healthylivingrepositories.IDailyCalorieIntakeRepository.class);
        var dailyCalorieIntakeService = new DailyCalorieIntakeService(iUserRepository,iDailyCalorieIntakeRepository);
        User user = new User();
        user.setEmail("mockyMcMockness@mockmail.mock");
        Optional<User> optional = Optional.of(user);

        when(authentication.getName()).thenReturn("user");
        when(iUserRepository.findByEmail("user")).thenReturn(optional);
        Long id = 1L;
        when(iDailyCalorieIntakeRepository.findDailyCalorieIntakeById(id)).thenReturn(null);
        DailyCalorieIntakeDTO dailyCalorieIntakeDTO = new DailyCalorieIntakeDTO(3000);
        DailyCalorieIntakeNotFoundException dailyCalorieIntakeNotFoundException =
                Assertions.assertThrows(DailyCalorieIntakeNotFoundException.class, () -> dailyCalorieIntakeService.edit(id, dailyCalorieIntakeDTO, authentication));
        String exceptionMessage = "Cant find the value";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Assertions.assertTrue(dailyCalorieIntakeNotFoundException.getMessage().contains(exceptionMessage));
        Assertions.assertEquals(dailyCalorieIntakeNotFoundException.getStatus(), httpStatus);
    }
    @Test
    public void whenSaveDailyCalorieIntake() throws DailyCalorieIntakeNoContentException {
        var iUserRepository = mock(IUserRepository.class);
        var authentication = mock(Authentication.class);
        var iDailyCalorieIntakeRepository =mock(com.greenfoxacademy.hta.repositories.healthylivingrepositories.IDailyCalorieIntakeRepository.class);
        var dailyCalorieIntakeService = new DailyCalorieIntakeService(iUserRepository,iDailyCalorieIntakeRepository);
        User user = new User();
        user.setEmail("mockyMcMockness@mockmail.mock");
        Optional<User> optional = Optional.of(user);

        when(authentication.getName()).thenReturn("user");
        when(iUserRepository.findByEmail("user")).thenReturn(optional);
        DailyCalorieIntakeDTO dailyCalorieIntakeDTO = new DailyCalorieIntakeDTO(1500);
        //when(user.getDailyCalories().add()).thenReturn(null);
        DailyCalorieIntakeDTO dailyCalorieIntakeDTOreturned = dailyCalorieIntakeService.save(dailyCalorieIntakeDTO,authentication);

        Assertions.assertEquals(dailyCalorieIntakeDTO.getCalorie(), dailyCalorieIntakeDTOreturned.getCalorie());
    }
    @Test
    public void exceptionWhenSaveDailyCalorieIntakeFieldIsZero() {
        var iUserRepository = mock(IUserRepository.class);
        var authentication = mock(Authentication.class);
        var iDailyCalorieIntakeRepository =mock(com.greenfoxacademy.hta.repositories.healthylivingrepositories.IDailyCalorieIntakeRepository.class);
        var dailyCalorieIntakeService = new DailyCalorieIntakeService(iUserRepository,iDailyCalorieIntakeRepository);
        User user = new User();
        user.setEmail("mockyMcMockness@mockmail.mock");
        Optional<User> optional = Optional.of(user);

        when(authentication.getName()).thenReturn("user");
        when(iUserRepository.findByEmail("user")).thenReturn(optional);
        DailyCalorieIntakeDTO dailyCalorieIntakeDTO = new DailyCalorieIntakeDTO(0);
        DailyCalorieIntakeNoContentException dailyCalorieIntakeNoContentException =
                Assertions.assertThrows(DailyCalorieIntakeNoContentException.class, ()-> dailyCalorieIntakeService.save(dailyCalorieIntakeDTO,authentication));
        String expectedMessage = "Please give a Calorie value!";
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        Assertions.assertTrue(dailyCalorieIntakeNoContentException.getMessage().contains(expectedMessage));
        Assertions.assertEquals(dailyCalorieIntakeNoContentException.getStatus(), httpStatus);
    }
    @Test
    public void exceptionWhenSaveDailyCalorieIntakeIsNull() {
        var iUserRepository = mock(IUserRepository.class);
        var authentication = mock(Authentication.class);
        var iDailyCalorieIntakeRepository =mock(com.greenfoxacademy.hta.repositories.healthylivingrepositories.IDailyCalorieIntakeRepository.class);
        var dailyCalorieIntakeService = new DailyCalorieIntakeService(iUserRepository,iDailyCalorieIntakeRepository);
        User user = new User();
        user.setEmail("mockyMcMockness@mockmail.mock");
        Optional<User> optional = Optional.of(user);

        when(authentication.getName()).thenReturn("user");
        when(iUserRepository.findByEmail("user")).thenReturn(optional);
        DailyCalorieIntakeDTO dailyCalorieIntakeDTO = null;
        DailyCalorieIntakeNoContentException dailyCalorieIntakeNoContentException = Assertions.assertThrows(DailyCalorieIntakeNoContentException.class, ()-> dailyCalorieIntakeService.save(dailyCalorieIntakeDTO,authentication));
        String expectedMessage = "Please give a Calorie value!";
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        Assertions.assertTrue(dailyCalorieIntakeNoContentException.getMessage().contains(expectedMessage));
        Assertions.assertEquals(dailyCalorieIntakeNoContentException.getStatus(), httpStatus);
    }
}
