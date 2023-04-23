package unittests.servicelayer.healthylivingunittests;

import com.greenfoxacademy.hta.dtos.healthylivingentitydto.DailyCalorieIntakeDTO;
import com.greenfoxacademy.hta.exceptions.DailyCalorieIntakeNoContentException;
import com.greenfoxacademy.hta.exceptions.DailyCalorieIntakeNotFoundException;
import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.models.healthylivingentities.DailyCalorieIntake;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.repositories.healthylivingrepositories.IDailyCalorieIntakeRepository;
import com.greenfoxacademy.hta.repositories.healthylivingrepositories.IExerciseTimeRepository;
import com.greenfoxacademy.hta.services.healthylivingservices.DailyCalorieIntakeServiceImp;
import com.greenfoxacademy.hta.services.healthylivingservices.ExerciseTimeServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditHealthyLivingEntitiesTests {
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
    public void init() {
        User user = new User();
        user.setEmail("mockyMcMockness@mockmail.mock");
        user.setDailyCalories(new ArrayList<DailyCalorieIntake>());
        Optional<User> optional = Optional.of(user);

        lenient().when(authentication.getName()).thenReturn("user");
        lenient().when(iUserRepository.findByEmail("user")).thenReturn(optional);
    }

    @Test
    public void whenEditingDailyCalorieIntake() throws DailyCalorieIntakeNotFoundException, DailyCalorieIntakeNoContentException {
        Long id = 1L;
        DailyCalorieIntake dailyCalorieIntake = new DailyCalorieIntake(id, 5000);
        when(IDailyCalorieIntakeRepository.findDailyCalorieIntakeById(id)).thenReturn(dailyCalorieIntake);
        DailyCalorieIntakeDTO dailyCalorieIntakeDTO = new DailyCalorieIntakeDTO(3000);
        dailyCalorieIntakeServiceImp.edit(id, dailyCalorieIntakeDTO, authentication);
        DailyCalorieIntake editedDailyCalorieIntake = IDailyCalorieIntakeRepository.findDailyCalorieIntakeById(id);
        Assertions.assertEquals(editedDailyCalorieIntake.getCalorie(), dailyCalorieIntakeDTO.getCalorie());
    }

    @Test
    public void exceptionWhenEditDailyCalorieIntakeNotFound() {
        Long id = 1L;
        when(IDailyCalorieIntakeRepository.findDailyCalorieIntakeById(id)).thenReturn(null);
        DailyCalorieIntakeDTO dailyCalorieIntakeDTO = new DailyCalorieIntakeDTO(3000);
        DailyCalorieIntakeNotFoundException dailyCalorieIntakeNotFoundException =
                Assertions.assertThrows(DailyCalorieIntakeNotFoundException.class, () -> dailyCalorieIntakeServiceImp.edit(id, dailyCalorieIntakeDTO, authentication));
        String exceptionMessage = "Cant find the value";
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Assertions.assertTrue(dailyCalorieIntakeNotFoundException.getMessage().contains(exceptionMessage));
        Assertions.assertTrue(dailyCalorieIntakeNotFoundException.getStatus().equals(httpStatus));
    }
}
