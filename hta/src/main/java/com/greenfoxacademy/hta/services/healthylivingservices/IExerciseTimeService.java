package com.greenfoxacademy.hta.services.healthylivingservices;

import com.greenfoxacademy.hta.dtos.healthylivingentitydto.ExerciseTimeDTO;
import com.greenfoxacademy.hta.exceptions.ExerciseTimeNoContentException;
import com.greenfoxacademy.hta.exceptions.ExerciseTimeNotFoundException;
import com.greenfoxacademy.hta.models.user.User;
import org.springframework.security.core.Authentication;

import java.util.List;
public interface IExerciseTimeService {
    ExerciseTimeDTO save(ExerciseTimeDTO exerciseTimeDTO, Authentication authentication) throws ExerciseTimeNoContentException;
    List<ExerciseTimeDTO> delete (Long id, Authentication authentication) throws ExerciseTimeNotFoundException;
    List<ExerciseTimeDTO> edit(Long id, ExerciseTimeDTO exerciseTimeDTO, Authentication authentication) throws ExerciseTimeNotFoundException;
    List<ExerciseTimeDTO> getAllByAuthentication (Authentication authentication);
    List<ExerciseTimeDTO> getAll (User user);
}
