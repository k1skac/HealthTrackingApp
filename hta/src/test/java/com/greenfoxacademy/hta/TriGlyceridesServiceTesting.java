package com.greenfoxacademy.hta;

import com.greenfoxacademy.hta.models.bloodlabdata.TriGlycerides;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.bloodlabdata.ITriGlyceridesRepository;
import com.greenfoxacademy.hta.services.bloodlabdata.IBloodLabDataService;
import com.greenfoxacademy.hta.services.bloodlabdata.ITriGlyceridesService;
import com.greenfoxacademy.hta.services.bloodlabdata.TriGlyceridesService;
import com.greenfoxacademy.hta.services.user.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import java.time.LocalDateTime;
import static org.mockito.Mockito.*;

public class TriGlyceridesServiceTesting {
    @Test
    public void whenSaveTriglyceridesOK () {

        // Arrange
        var userServiceMock = mock(IUserService.class);
        var authentication = mock(Authentication.class);
        var triglyceridesRepositoryMock = mock(ITriGlyceridesRepository.class);
        var bloodLabDataServiceMock = mock(IBloodLabDataService.class);

        ITriGlyceridesService triGlyceridesService = new TriGlyceridesService(triglyceridesRepositoryMock, userServiceMock, bloodLabDataServiceMock );
        User user = new User();
        user.setEmail("mockyMcMockness@mockmail.mock");
        LocalDateTime time = LocalDateTime.now();
        TriGlycerides triGlycerides = new TriGlycerides( 77, time);

        when(authentication.getName()).thenReturn("user");
        when(userServiceMock.findByEmail("user")).thenReturn(user);

        // Act
        triGlyceridesService.save(triGlycerides, authentication.getName());

        // Assert
        verify(triglyceridesRepositoryMock).save(new TriGlycerides (null, 77, time, user.getBloodLabData()));
    }
}
