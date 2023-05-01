package com.greenfoxacademy.hta.services.healthylivingservices;

import com.greenfoxacademy.hta.dtos.healthylivingentitydto.DailyCalorieIntakeDTO;
import com.greenfoxacademy.hta.exceptions.DailyCalorieIntakeNoContentException;
import com.greenfoxacademy.hta.exceptions.DailyCalorieIntakeNotFoundException;
import com.greenfoxacademy.hta.models.user.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IDailyCalorieIntakeService {
    DailyCalorieIntakeDTO save(DailyCalorieIntakeDTO dailyCalorieIntakeDTO, Authentication authentication) throws DailyCalorieIntakeNoContentException;
    List<DailyCalorieIntakeDTO> delete (Long id, Authentication authentication) throws DailyCalorieIntakeNotFoundException;
    List<DailyCalorieIntakeDTO> edit(Long id,DailyCalorieIntakeDTO dailyCalorieIntakeDTO, Authentication authentication) throws DailyCalorieIntakeNotFoundException, DailyCalorieIntakeNoContentException;
    List<DailyCalorieIntakeDTO> getAllByAuthentication (Authentication authentication);
    List<DailyCalorieIntakeDTO> getAll (User user);
}
