package unittests.servicelayer.healthylivingunittests;

import com.greenfoxacademy.hta.dtos.healthylivingentitydto.DailyCalorieIntakeDTO;
import com.greenfoxacademy.hta.dtos.healthylivingentitydto.ExerciseTimeDTO;
import com.greenfoxacademy.hta.exceptions.DailyCalorieIntakeNoContentException;
import com.greenfoxacademy.hta.exceptions.ExerciseTimeNoContentException;
import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.models.healthylivingentities.DailyCalorieIntake;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.repositories.healthylivingrepositories.IDailyCalorieIntakeRepository;
import com.greenfoxacademy.hta.repositories.healthylivingrepositories.IExerciseTimeRepository;
import com.greenfoxacademy.hta.services.healthylivingservices.DailyCalorieIntakeServiceImp;
import com.greenfoxacademy.hta.services.healthylivingservices.ExerciseTimeServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class SaveNewHealthyLivingEntitiesTest {
    @Mock
    private IDailyCalorieIntakeRepository IDailyCalorieIntakeRepository;
    @Mock
    private IExerciseTimeRepository IExerciseTimeRepository;
    @Mock
    private IUserRepository iUserRepository;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private DailyCalorieIntakeServiceImp dailyCalorieIntakeServiceImp;
    @InjectMocks
    private ExerciseTimeServiceImp exerciseTimeServiceImp;
    @BeforeEach
    public void init () {
        User user = new User();
        user.setEmail("mockyMcMockness@mockmail.mock");
        user.setDailyCalories(new ArrayList<DailyCalorieIntake>());
        Optional<User> optional = Optional.of(user);

        lenient().when(authentication.getName()).thenReturn("user");
        lenient().when(iUserRepository.findByEmail("user")).thenReturn(optional);
    }
    @Test
    public void whenSaveDailyCalorieIntake() throws DailyCalorieIntakeNoContentException {
        DailyCalorieIntakeDTO dailyCalorieIntakeDTO = new DailyCalorieIntakeDTO(1500);
        DailyCalorieIntakeDTO dailyCalorieIntakeDTOreturned = dailyCalorieIntakeServiceImp.save(dailyCalorieIntakeDTO,authentication);
        Assertions.assertEquals(dailyCalorieIntakeDTO.getCalorie(), dailyCalorieIntakeDTOreturned.getCalorie());
    }
    @Test
    public void exceptionWhenSaveDailyCalorieIntakeFieldIsZero() {
        DailyCalorieIntakeDTO dailyCalorieIntakeDTO = new DailyCalorieIntakeDTO(0);
        DailyCalorieIntakeNoContentException dailyCalorieIntakeNoContentException =
                Assertions.assertThrows(DailyCalorieIntakeNoContentException.class, ()-> dailyCalorieIntakeServiceImp.save(dailyCalorieIntakeDTO,authentication));
        String expectedMessage = "Please give a Calorie value!";
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        Assertions.assertTrue(dailyCalorieIntakeNoContentException.getMessage().contains(expectedMessage));
        Assertions.assertTrue(dailyCalorieIntakeNoContentException.getStatus().equals(httpStatus));
    }
    @Test
    public void exceptionWhenSaveDailyCalorieIntakeIsNull() {
        DailyCalorieIntakeDTO dailyCalorieIntakeDTO = null;
        DailyCalorieIntakeNoContentException dailyCalorieIntakeNoContentException = Assertions.assertThrows(DailyCalorieIntakeNoContentException.class, ()-> dailyCalorieIntakeServiceImp.save(dailyCalorieIntakeDTO,authentication));
        String expectedMessage = "Please give a Calorie value!";
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        Assertions.assertTrue(dailyCalorieIntakeNoContentException.getMessage().contains(expectedMessage));
        Assertions.assertTrue(dailyCalorieIntakeNoContentException.getStatus().equals(httpStatus));
    }
    @Test
    public void whenSaveExerciseTime () throws ExerciseTimeNoContentException {
        ExerciseTimeDTO exerciseTimeDTO = new ExerciseTimeDTO(new Timestamp(60000),500);
        ExerciseTimeDTO exerciseTimeDTOreturned = exerciseTimeServiceImp.save(exerciseTimeDTO,authentication);
        Assertions.assertEquals(exerciseTimeDTO.getDailyActiveTime(),exerciseTimeDTOreturned.getDailyActiveTime());
        Assertions.assertEquals(exerciseTimeDTO.getBurntCalorie(),exerciseTimeDTOreturned.getBurntCalorie());
    }
    @Test
    public void exceptionWhenSaveExerciseTimeActiveTimeIsZero() {
        ExerciseTimeDTO exerciseTimeDTO = new ExerciseTimeDTO(new Timestamp(0),500);
        ExerciseTimeNoContentException exerciseTimeNoContentException =
                Assertions.assertThrows(ExerciseTimeNoContentException.class, () -> exerciseTimeServiceImp.save(exerciseTimeDTO,authentication));
        String expectedMessage = "Exercise Time has no content";
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        Assertions.assertTrue(exerciseTimeNoContentException.getMessage().contains(expectedMessage));
        Assertions.assertTrue(exerciseTimeNoContentException.getStatus().equals(httpStatus));
    }
    @Test
    public void exceptionWhenSaveExerciseTimeBurnedCalorieIsZero() {
        ExerciseTimeDTO exerciseTimeDTO = new ExerciseTimeDTO(new Timestamp(10000),0);
        ExerciseTimeNoContentException exerciseTimeNoContentException =
                Assertions.assertThrows(ExerciseTimeNoContentException.class, () -> exerciseTimeServiceImp.save(exerciseTimeDTO,authentication));
        String expectedMessage = "Exercise Time has no content";
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        Assertions.assertTrue(exerciseTimeNoContentException.getMessage().contains(expectedMessage));
        Assertions.assertTrue(exerciseTimeNoContentException.getStatus().equals(httpStatus));
    }
    @Test
    public void exceptionWhenDailyActiveTimeIsNull() {
        ExerciseTimeDTO exerciseTimeDTO = null;
        ExerciseTimeNoContentException exerciseTimeNoContentException =
                Assertions.assertThrows(ExerciseTimeNoContentException.class, () -> exerciseTimeServiceImp.save(exerciseTimeDTO,authentication));
        String expectedMessage = "Exercise Time has no content";
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        Assertions.assertTrue(exerciseTimeNoContentException.getMessage().contains(expectedMessage));
        Assertions.assertTrue(exerciseTimeNoContentException.getStatus().equals(httpStatus));
    }

}
