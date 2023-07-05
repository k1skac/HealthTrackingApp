package com.greenfoxacademy.hta;

import com.greenfoxacademy.hta.dtos.saveuserdatadto.SaveHeartRateDTO;
import com.greenfoxacademy.hta.exceptions.HeartRateBadRequestException;
import com.greenfoxacademy.hta.models.user.HeartRate;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.IHeartRateRepository;
import com.greenfoxacademy.hta.services.filemanagment.IFileDataService;
import com.greenfoxacademy.hta.services.heartrate.HeartRateService;
import com.greenfoxacademy.hta.services.heartrate.IHeartRateService;
import com.greenfoxacademy.hta.services.user.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import java.time.LocalDateTime;
import static org.mockito.Mockito.*;

public class HeartRateServiceTesting {

    @Test
    public void whenSaveHeartRateNotFoundThrowsException()  {

        // Arrange
        var userServiceMock = mock(IUserService.class);
        var authentication = mock(Authentication.class);
        var fileDataServiceMock = mock(IFileDataService.class);
        var heartRateRepositoryMock = mock(IHeartRateRepository.class);
        IHeartRateService heartRateService = new HeartRateService(heartRateRepositoryMock, userServiceMock, fileDataServiceMock);
        User user = new User();
        user.setEmail("HeartRateNess@dead.com");
        LocalDateTime time = LocalDateTime.now();
        SaveHeartRateDTO saveHeartRateDTOWithHeartRateNull = new SaveHeartRateDTO(time, 0f, null);
        SaveHeartRateDTO saveHeartRateDTOWithMeasuredAtNull = new SaveHeartRateDTO(null, 90f, null);

        when(authentication.getName()).thenReturn("user");
        when(userServiceMock.findByEmail("user")).thenReturn(user);

        // Act & Assert
        Assertions.assertThrows(HeartRateBadRequestException.class, () -> heartRateService.save(saveHeartRateDTOWithHeartRateNull, authentication));
        Assertions.assertThrows(HeartRateBadRequestException.class, () -> heartRateService.save(saveHeartRateDTOWithMeasuredAtNull, authentication));

    }

    @Test
    public void whenSaveHeartRateOK () throws Exception {
        // Arrange
        var userServiceMock = mock(IUserService.class);
        var authentication = mock(Authentication.class);
        var heartRateRepositoryMock = mock(IHeartRateRepository.class);
        var fileDataServiceMock = mock(IFileDataService.class);

        IHeartRateService heartRateService = new HeartRateService(heartRateRepositoryMock, userServiceMock, fileDataServiceMock);
        User user = new User();
        user.setEmail("mockyMcMockness@mockmail.mock");
        LocalDateTime time = LocalDateTime.now();
        SaveHeartRateDTO saveHeartRateDTO = new SaveHeartRateDTO( time, 77, null);

        when(authentication.getName()).thenReturn("user");
        when(userServiceMock.findByEmail("user")).thenReturn(user);

        // Act
         heartRateService.save(saveHeartRateDTO, authentication);

        // Assert
        verify(heartRateRepositoryMock).save(new HeartRate(null, time, 77, user, null));
    }
}
