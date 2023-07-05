package com.greenfoxacademy.hta;

import com.greenfoxacademy.hta.models.bloodlabdata.Glucose;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.bloodlabdata.IGlucoseRepository;
import com.greenfoxacademy.hta.services.bloodlabdata.GlucoseService;
import com.greenfoxacademy.hta.services.bloodlabdata.IBloodLabDataService;
import com.greenfoxacademy.hta.services.bloodlabdata.IGlucoseService;
import com.greenfoxacademy.hta.services.user.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import java.time.LocalDateTime;
import static org.mockito.Mockito.*;

public class GlucoseServiceTesting {

    @Test
    public void whenSaveGlucoseOk(){

        // Arrange
        var userServiceMock = mock(IUserService.class);
        var authentication = mock(Authentication.class);
        var bloodLabDataServiceMock = mock(IBloodLabDataService.class);
        var glucoseRepositoryMock = mock(IGlucoseRepository.class);
        IGlucoseService iGlucoseService = new GlucoseService(glucoseRepositoryMock, userServiceMock, bloodLabDataServiceMock );
        User user = new User();
        user.setEmail("mrGlucose@glucose.com");
        LocalDateTime time = LocalDateTime.now();
        Glucose saveGlucose = new Glucose( 2, time );

        when(authentication.getName()).thenReturn("user");
        when(userServiceMock.findByEmail("user")).thenReturn(user);
        // Act

        iGlucoseService.save(saveGlucose, authentication.getName());

        //Assert
        verify(glucoseRepositoryMock).save(new Glucose(null, 2, time, user.getBloodLabData()));
    }
}
