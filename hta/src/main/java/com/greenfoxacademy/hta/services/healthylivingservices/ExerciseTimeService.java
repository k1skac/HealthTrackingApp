package com.greenfoxacademy.hta.services.healthylivingservices;

import com.greenfoxacademy.hta.dtos.healthylivingentitydto.DailyCalorieIntakeDTO;
import com.greenfoxacademy.hta.dtos.healthylivingentitydto.ExerciseTimeDTO;
import com.greenfoxacademy.hta.exceptions.ExerciseTimeNoContentException;
import com.greenfoxacademy.hta.exceptions.ExerciseTimeNotFoundException;
import com.greenfoxacademy.hta.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
public interface ExerciseTimeService {
    ExerciseTimeDTO save(ExerciseTimeDTO exerciseTimeDTO, Authentication authentication) throws ExerciseTimeNoContentException;
    List<ExerciseTimeDTO> delete (Long id, Authentication authentication) throws ExerciseTimeNotFoundException;
    List<ExerciseTimeDTO> edit(Long id, ExerciseTimeDTO exerciseTimeDTO, Authentication authentication) throws ExerciseTimeNotFoundException;
    List<ExerciseTimeDTO> getAllbyAuthentication (Authentication authentication);
    List<ExerciseTimeDTO> getAll (User user);
}
