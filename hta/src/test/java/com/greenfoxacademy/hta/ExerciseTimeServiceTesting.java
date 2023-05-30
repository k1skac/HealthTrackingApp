package com.greenfoxacademy.hta;

import com.greenfoxacademy.hta.dtos.healthylivingentitydto.ExerciseTimeDTO;
import com.greenfoxacademy.hta.exceptions.ExerciseTimeNoContentException;
import com.greenfoxacademy.hta.models.healthylivingentities.ExerciseTime;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.services.healthylivingservices.ExerciseTimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class ExerciseTimeServiceTesting {
    @Test
    public void whenSaveExerciseTime () throws ExerciseTimeNoContentException {
        var iUserRepository = mock(IUserRepository.class);
        var authentication = mock(Authentication.class);
        var iExerciseTimeRepository =mock(com.greenfoxacademy.hta.repositories.healthylivingrepositories.IExerciseTimeRepository.class);
        var exerciseTimeService = new ExerciseTimeService(iUserRepository,iExerciseTimeRepository);
        User user = new User();
        user.setEmail("mockyMcMockness@mockmail.mock");
        Optional<User> optional = Optional.of(user);

        when(authentication.getName()).thenReturn("user");
        when(iUserRepository.findByEmail("user")).thenReturn(optional);
        ExerciseTimeDTO exerciseTimeDTO = new ExerciseTimeDTO(15L,500);
        ExerciseTime exerciseTime = new ExerciseTime(exerciseTimeDTO.getDailyActiveTimeInMinutes(), exerciseTimeDTO.getBurntCalorie(), user);
        when(iExerciseTimeRepository.save(any())).thenReturn(exerciseTime);
        ExerciseTimeDTO exerciseTimeDTOReturned = exerciseTimeService.saveExerciseTime(exerciseTimeDTO,authentication);
        Assertions.assertEquals(exerciseTimeDTO.getDailyActiveTimeInMinutes(),exerciseTimeDTOReturned.getDailyActiveTimeInMinutes());
        Assertions.assertEquals(exerciseTimeDTO.getBurntCalorie(),exerciseTimeDTOReturned.getBurntCalorie());
    }
    @Test
    public void exceptionWhenSaveExerciseTimeActiveTimeIsZero() {
        var iUserRepository = mock(IUserRepository.class);
        var authentication = mock(Authentication.class);
        var iExerciseTimeRepository =mock(com.greenfoxacademy.hta.repositories.healthylivingrepositories.IExerciseTimeRepository.class);
        var exerciseTimeService = new ExerciseTimeService(iUserRepository,iExerciseTimeRepository);
        User user = new User();
        user.setEmail("mockyMcMockness@mockmail.mock");
        Optional<User> optional = Optional.of(user);

        when(authentication.getName()).thenReturn("user");
        when(iUserRepository.findByEmail("user")).thenReturn(optional);

        ExerciseTimeDTO exerciseTimeDTO = new ExerciseTimeDTO(0L,500);
        ExerciseTimeNoContentException exerciseTimeNoContentException =
                Assertions.assertThrows(ExerciseTimeNoContentException.class, () -> exerciseTimeService.saveExerciseTime(exerciseTimeDTO,authentication));
        String expectedMessage = "Exercise Time has no content";
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        Assertions.assertTrue(exerciseTimeNoContentException.getMessage().contains(expectedMessage));
        Assertions.assertTrue(exerciseTimeNoContentException.getStatus().equals(httpStatus));
    }
    @Test
    public void exceptionWhenSaveExerciseTimeBurnedCalorieIsZero() {
        var iUserRepository = mock(IUserRepository.class);
        var authentication = mock(Authentication.class);
        var iExerciseTimeRepository =mock(com.greenfoxacademy.hta.repositories.healthylivingrepositories.IExerciseTimeRepository.class);
        var exerciseTimeService = new ExerciseTimeService(iUserRepository,iExerciseTimeRepository);
        User user = new User();
        user.setEmail("mockyMcMockness@mockmail.mock");
        Optional<User> optional = Optional.of(user);

        when(authentication.getName()).thenReturn("user");
        when(iUserRepository.findByEmail("user")).thenReturn(optional);
        ExerciseTimeDTO exerciseTimeDTO = new ExerciseTimeDTO(0L,0);
        ExerciseTimeNoContentException exerciseTimeNoContentException =
                Assertions.assertThrows(ExerciseTimeNoContentException.class, () -> exerciseTimeService.saveExerciseTime(exerciseTimeDTO,authentication));
        String expectedMessage = "Exercise Time has no content";
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        Assertions.assertTrue(exerciseTimeNoContentException.getMessage().contains(expectedMessage));
        Assertions.assertTrue(exerciseTimeNoContentException.getStatus().equals(httpStatus));
    }
    @Test
    public void exceptionWhenDailyActiveTimeIsNull() {
        var iUserRepository = mock(IUserRepository.class);
        var authentication = mock(Authentication.class);
        var iExerciseTimeRepository =mock(com.greenfoxacademy.hta.repositories.healthylivingrepositories.IExerciseTimeRepository.class);
        var exerciseTimeService = new ExerciseTimeService(iUserRepository,iExerciseTimeRepository);
        User user = new User();
        user.setEmail("mockyMcMockness@mockmail.mock");
        Optional<User> optional = Optional.of(user);

        when(authentication.getName()).thenReturn("user");
        when(iUserRepository.findByEmail("user")).thenReturn(optional);
        ExerciseTimeDTO exerciseTimeDTO = null;
        ExerciseTimeNoContentException exerciseTimeNoContentException =
                Assertions.assertThrows(ExerciseTimeNoContentException.class, () -> exerciseTimeService.saveExerciseTime(exerciseTimeDTO,authentication));
        String expectedMessage = "Exercise Time has no content";
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        Assertions.assertTrue(exerciseTimeNoContentException.getMessage().contains(expectedMessage));
        Assertions.assertTrue(exerciseTimeNoContentException.getStatus().equals(httpStatus));
    }
}
