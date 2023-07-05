package com.greenfoxacademy.hta.services.healthylivingservices;

import com.greenfoxacademy.hta.dtos.saveuserdatadto.SaveWeightDTO;
import com.greenfoxacademy.hta.exceptions.WeightBadRequestException;
import com.greenfoxacademy.hta.exceptions.WeightNotFoundException;
import com.greenfoxacademy.hta.models.user.User;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface IWeightService {
    void save(SaveWeightDTO saveWeightDTO, Authentication authentication) throws IOException, WeightBadRequestException;
    List<SaveWeightDTO> getAll(Authentication authentication);
    List<SaveWeightDTO> edit(Long id, Authentication authentication, SaveWeightDTO saveWeightDTO) throws WeightNotFoundException;
    List<SaveWeightDTO> delete(Long id, Authentication authentication) throws WeightNotFoundException;
    int getWeightForNotification(User user, LocalDateTime date);
    String notificationMessage(User user, LocalDateTime today);
}
