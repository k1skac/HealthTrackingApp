package com.greenfoxacademy.hta;

import com.greenfoxacademy.hta.models.bloodlabdata.Calcium;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.bloodlabdata.ICalciumRepository;
import com.greenfoxacademy.hta.services.bloodlabdata.*;
import com.greenfoxacademy.hta.services.user.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import java.time.LocalDateTime;
import static org.mockito.Mockito.*;

public class CalciumServiceTesting {

    @Test
    public void whenSaveCalciumOk() {

        // Arrange
        var userServiceMock = mock(IUserService.class);
        var authentication = mock(Authentication.class);
        var bloodLabDataServiceMock = mock(IBloodLabDataService.class);
        var calciumRepositoryMock = mock(ICalciumRepository.class);
        ICalciumService iCalciumService = new CalciumService(userServiceMock, calciumRepositoryMock, bloodLabDataServiceMock );
        User user = new User();
        user.setEmail("mrCalcium@os.com");
        LocalDateTime time = LocalDateTime.now();
        Calcium saveCalcium = new Calcium( 4, time );

        when(authentication.getName()).thenReturn("user");
        when(userServiceMock.findByEmail("user")).thenReturn(user);
        // Act

        iCalciumService.save(saveCalcium, authentication.getName());

        //Assert
        verify(calciumRepositoryMock).save(new Calcium(null, 4, time, user.getBloodLabData()));
    }
}
