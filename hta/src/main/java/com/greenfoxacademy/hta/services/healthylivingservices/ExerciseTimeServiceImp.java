package com.greenfoxacademy.hta.services.healthylivingservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.hta.dtos.healthylivingentitydto.ExerciseTimeDTO;
import com.greenfoxacademy.hta.exceptions.ExerciseTimeNoContentException;
import com.greenfoxacademy.hta.exceptions.ExerciseTimeNotFoundException;
import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.models.healthylivingentities.ExerciseTime;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.repositories.healthylivingrepositories.IExerciseTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseTimeServiceImp implements ExerciseTimeService {
    private final IUserRepository iUserRepository;
    private final IExerciseTimeRepository IExerciseTimeRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ExerciseTimeDTO save(ExerciseTimeDTO exerciseTimeDTO, Authentication authentication) throws ExerciseTimeNoContentException {
        if (exerciseTimeDTO == null || exerciseTimeDTO.getDailyActiveTime().getTime() == 0 || exerciseTimeDTO.getBurntCalorie() == 0) {
            throw new ExerciseTimeNoContentException();
        }
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        ExerciseTime exerciseTime = new ExerciseTime(exerciseTimeDTO.getDailyActiveTime().getTime(), exerciseTimeDTO.getBurntCalorie(), user);
        user.getDailyExercise().add(exerciseTime);
        IExerciseTimeRepository.save(exerciseTime);
        iUserRepository.save(user);
        //ToDo LogSave
        return objectMapper.convertValue(exerciseTime, ExerciseTimeDTO.class);
    }
    @Override
    public List<ExerciseTimeDTO> delete(Long id, Authentication authentication) throws ExerciseTimeNotFoundException {
        ExerciseTime exerciseTime = IExerciseTimeRepository.findExerciseTimeById(id);
        if (exerciseTime == null) {
            throw new ExerciseTimeNotFoundException();
        }
        IExerciseTimeRepository.delete(exerciseTime);
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        user.getDailyExercise().remove(exerciseTime);
        iUserRepository.save(user);
        //ToDo LogSave
        return getAll(user);
    }
    @Override
    public List<ExerciseTimeDTO> edit(Long id, ExerciseTimeDTO exerciseTimeDTO, Authentication authentication) throws ExerciseTimeNotFoundException {
        ExerciseTime exerciseTime = IExerciseTimeRepository.findExerciseTimeById(id);
        if (exerciseTime == null) {
            throw new ExerciseTimeNotFoundException();
        }
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        exerciseTime.setDailyActiveTime(exerciseTimeDTO.getDailyActiveTime());
        exerciseTime.setBurntCalorie(exerciseTimeDTO.getBurntCalorie());
        IExerciseTimeRepository.save(exerciseTime);
        //ToDo LogSave
        return getAll(user);
    }

    @Override
    public List<ExerciseTimeDTO> getAllbyAuthentication(Authentication authentication) {
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        return IExerciseTimeRepository.findAllByUser(user)
                .stream()
                .map(exerciseTime -> objectMapper.convertValue(exerciseTime, ExerciseTimeDTO.class))
                .toList();
        //Exam if not empty. Create exception.
    }

    @Override
    public List<ExerciseTimeDTO> getAll(User user) {
        return IExerciseTimeRepository.findAllByUser(user)
                .stream()
                .map(exerciseTime -> objectMapper.convertValue(exerciseTime, ExerciseTimeDTO.class))
                .toList();
        //Exam if not empty. Create exception.
    }
}
