package com.greenfoxacademy.hta;

import com.greenfoxacademy.hta.dtos.saveuserdatadto.SaveBloodPressureDTO;
import com.greenfoxacademy.hta.exceptions.BloodPressureBadRequestException;
import com.greenfoxacademy.hta.models.user.BloodPressure;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.IBloodPressureRepository;
import com.greenfoxacademy.hta.services.bloodpressure.BloodPressureService;
import com.greenfoxacademy.hta.services.bloodpressure.IBloodPressureService;
import com.greenfoxacademy.hta.services.filemanagment.IFileDataService;
import com.greenfoxacademy.hta.services.user.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import static org.mockito.Mockito.*;

public class BloodPressureServiceTesting {
    @Test
    public void whenSaveBloodPressureIsOk() throws Exception {

        //Arrange
        var userServiceMock = mock(IUserService.class);
        var authentication = mock(Authentication.class);
        var bloodPressureRepositoryMock = mock(IBloodPressureRepository.class);
        var fileDataServiceMock = mock(IFileDataService.class);
        var bloodPressureService = new BloodPressureService(bloodPressureRepositoryMock, userServiceMock, fileDataServiceMock);
        User user = new User();
        user.setEmail("mockyMcMockness@mockmail.mock");

        SaveBloodPressureDTO saveBloodPressureDTO = new SaveBloodPressureDTO(LocalDateTime.now(), 130.f, 99f, null);
        Long id = 1L;
        BloodPressure bloodPressure = new BloodPressure(id, saveBloodPressureDTO.getBloodPressureMeasuredAt(), saveBloodPressureDTO.getSystolic(), saveBloodPressureDTO.getDiastolic(), user, null);
        when(authentication.getName()).thenReturn("user");
        when(userServiceMock.findByEmail("user")).thenReturn(user);

        //Act
        bloodPressureService.save(saveBloodPressureDTO, authentication);

        //Assert
        if (bloodPressureRepositoryMock.findById(id).isPresent()){
            BloodPressure savedBloodPressureReturned = bloodPressureRepositoryMock.findById(id).get();
            Assertions.assertEquals(bloodPressure, savedBloodPressureReturned);
            Assertions.assertEquals(bloodPressure.getBloodPressureMeasuredAt(), savedBloodPressureReturned.getBloodPressureMeasuredAt());
            Assertions.assertEquals(bloodPressure.getDiastolic(), savedBloodPressureReturned.getDiastolic());
            Assertions.assertEquals(bloodPressure.getSystolic(), savedBloodPressureReturned.getSystolic());
        }
    }

    @Test
    public void whenSaveBloodPressureOk() throws Exception {

        // Arrange
        var bloodPressureRepositoryMock = mock(IBloodPressureRepository.class);
        var userServiceMock = mock(IUserService.class);
        var authentication = mock(Authentication.class);
        var fileDataServiceMock = mock(IFileDataService.class);
        IBloodPressureService bloodPressureService = new BloodPressureService(bloodPressureRepositoryMock, userServiceMock, fileDataServiceMock);
        User user = new User();
        user.setEmail("mrBloodPressure@bloody.com");
        LocalDateTime time = LocalDateTime.now();
        SaveBloodPressureDTO saveBloodPressureDTO = new SaveBloodPressureDTO(time, 120, 88, null);
        when(authentication.getName()).thenReturn("user");
        when(userServiceMock.findByEmail("user")).thenReturn(user);

        // Act
            bloodPressureService.save(saveBloodPressureDTO, authentication);

        // Assert
        verify(bloodPressureRepositoryMock).save(new BloodPressure(null, time, 120f, 88f,user, null));
    }

    @Test
    public void whenSaveBloodPressureThrowsException()  {

        // Arrange
        var userServiceMock = mock(IUserService.class);
        var authentication = mock(Authentication.class);
        var fileDataServiceMock = mock(IFileDataService.class);
        var bloodPressureRepositoryMock = mock(IBloodPressureRepository.class);
        IBloodPressureService bloodPressureService = new BloodPressureService(bloodPressureRepositoryMock, userServiceMock, fileDataServiceMock);
        MultipartFile file = new MockMultipartFile("test", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test version".getBytes());
        User user = new User();
        user.setEmail("mrBloodPressure@bloody.com");
        LocalDateTime time = LocalDateTime.now();
        SaveBloodPressureDTO saveBloodPressureDTOWithSystolicNull = new SaveBloodPressureDTO(time, 0f, 88, file);
        SaveBloodPressureDTO saveBloodPressureDTOWithDiastolicNull = new SaveBloodPressureDTO(time, 120, 0f, file);
        SaveBloodPressureDTO saveBloodPressureDTOWithMeasuredAtNull = new SaveBloodPressureDTO(null, 120f, 88, file);

        when(authentication.getName()).thenReturn("user");
        when(userServiceMock.findByEmail("user")).thenReturn(user);

        // Act & Assert
        Assertions.assertThrows(BloodPressureBadRequestException.class, () -> bloodPressureService.save(saveBloodPressureDTOWithSystolicNull, authentication));
        Assertions.assertThrows(BloodPressureBadRequestException.class, () -> bloodPressureService.save(saveBloodPressureDTOWithDiastolicNull, authentication));
        Assertions.assertThrows(BloodPressureBadRequestException.class, () -> bloodPressureService.save(saveBloodPressureDTOWithMeasuredAtNull, authentication));
    }
}
