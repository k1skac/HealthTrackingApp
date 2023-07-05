package com.greenfoxacademy.hta;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.hta.dtos.saveuserdatadto.SaveWeightDTO;
import com.greenfoxacademy.hta.exceptions.WeightBadRequestException;
import com.greenfoxacademy.hta.exceptions.WeightNotFoundException;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.models.user.Weight;
import com.greenfoxacademy.hta.repositories.healthylivingrepositories.IWeightRepository;
import com.greenfoxacademy.hta.services.filemanagment.IFileDataService;
import com.greenfoxacademy.hta.services.healthylivingservices.IWeightService;
import com.greenfoxacademy.hta.services.healthylivingservices.WeightService;
import com.greenfoxacademy.hta.services.user.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class WeightServiceTesting {

    @Test
    public void whenSaveWeightOK () throws Exception {
        // Arrange
        var userServiceMock = mock(IUserService.class);
        var authentication = mock(Authentication.class);
        var weightRepositoryMock = mock(IWeightRepository.class);
        var fileDataServiceMock = mock(IFileDataService.class);
        var objectMapper = mock(ObjectMapper.class);

        IWeightService weightService = new WeightService(userServiceMock, weightRepositoryMock, objectMapper, fileDataServiceMock);
        User user = new User();
        user.setEmail("mockyMcMockness@mockmail.mock");
        LocalDateTime time = LocalDateTime.now();
        SaveWeightDTO saveWeightDTO = new SaveWeightDTO( 77, time, null);

        when(authentication.getName()).thenReturn("user");
        when(userServiceMock.findByEmail("user")).thenReturn(user);

        // Act
        weightService.save(saveWeightDTO, authentication);

        // Assert
        verify(weightRepositoryMock).save(new Weight(null, time, 77, user, null));
    }

    @Test
    public void whenSaveWeightThrowsException()  {

        // Arrange
        var userServiceMock = mock(IUserService.class);
        var authentication = mock(Authentication.class);
        var weightRepositoryMock = mock(IWeightRepository.class);
        var fileDataServiceMock = mock(IFileDataService.class);
        var objectMapper =  new ObjectMapper();
        WeightService weightService = new WeightService(userServiceMock, weightRepositoryMock, objectMapper, fileDataServiceMock);
        User user = new User();
        user.setEmail("weightyUser@weight.mock");

        LocalDateTime time = LocalDateTime.now();
        SaveWeightDTO saveWeightDTOWithWeightNull = new SaveWeightDTO(0f, time, null);
        SaveWeightDTO saveWeightDTOWithMeasuredAtNull = new SaveWeightDTO(120f, null, null);
        when(authentication.getName()).thenReturn("user");
        when(userServiceMock.findByEmail("user")).thenReturn(user);


        // Act & Assert

        Assertions.assertThrows(WeightBadRequestException.class, () -> weightService.save(saveWeightDTOWithWeightNull, authentication));
        Assertions.assertThrows(WeightBadRequestException.class, () -> weightService.save(saveWeightDTOWithMeasuredAtNull, authentication));
    }


    @Test
    public void whenDeleteWeightOK() throws Exception {

        // Arrange
        var userServiceMock = mock(IUserService.class);
        var authentication = mock(Authentication.class);
        var weightRepositoryMock = mock(IWeightRepository.class);
        var fileDataServiceMock = mock(IFileDataService.class);
        var objectMapper = mock(ObjectMapper.class);
        WeightService weightService = new WeightService(userServiceMock, weightRepositoryMock, objectMapper, fileDataServiceMock);
        User user = new User();
        user.setEmail("weightyUser@weight.mock");
        Long id = 1L;
        LocalDateTime time = LocalDateTime.now();
        Weight weight = new Weight(id, time, 120, user, null);
        when(authentication.getName()).thenReturn("user");
        when(userServiceMock.findByEmail("user")).thenReturn(user);
        when(weightRepositoryMock.findById(id)).thenReturn(Optional.of(weight));
        when(weightRepositoryMock.findWeightById(id)).thenReturn(weight);

        // Act
        weightService.delete(id, authentication);

        // Assert
        verify(weightRepositoryMock, times(1)).delete(weight);

    }

    @Test
    public void whenDeleteWeightThrowsException() {

        // Arrange
        var userServiceMock = mock(IUserService.class);
        var authentication = mock(Authentication.class);
        var weightRepositoryMock = mock(IWeightRepository.class);
        var fileDataServiceMock = mock(IFileDataService.class);
        var objectMapper =  new ObjectMapper();
        WeightService weightService = new WeightService(userServiceMock, weightRepositoryMock, objectMapper, fileDataServiceMock);
        User user = new User();
        user.setEmail("weightyUser@weight.mock");
        Long id = 1L;

        when(authentication.getName()).thenReturn("user");
        when(userServiceMock.findByEmail("user")).thenReturn(user);

        // Act & Assert
        Assertions.assertThrows(WeightNotFoundException.class, () -> weightService.delete(id, authentication));
        Assertions.assertThrows(WeightNotFoundException.class, () -> weightService.delete(id, authentication));
    }

}
