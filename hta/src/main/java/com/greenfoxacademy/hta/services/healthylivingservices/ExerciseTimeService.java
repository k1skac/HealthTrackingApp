package com.greenfoxacademy.hta.services.healthylivingservices;

import com.greenfoxacademy.hta.dtos.healthylivingentitydto.ExerciseTimeDTO;
import com.greenfoxacademy.hta.exceptions.ExerciseTimeNoContentException;
import com.greenfoxacademy.hta.exceptions.ExerciseTimeNotFoundException;
import com.greenfoxacademy.hta.models.healthylivingentities.ExerciseTime;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.repositories.healthylivingrepositories.IExerciseTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseTimeService implements IExerciseTimeService {
    private final IUserRepository iUserRepository;
    private final IExerciseTimeRepository IExerciseTimeRepository;

    @Override
    public ExerciseTimeDTO saveExerciseTime(ExerciseTimeDTO exerciseTimeDTO, Authentication authentication) throws ExerciseTimeNoContentException {
        if (exerciseTimeDTO == null || exerciseTimeDTO.getDailyActiveTimeInMinutes() == 0 || exerciseTimeDTO.getBurntCalorie() == 0) {
            throw new ExerciseTimeNoContentException();
        }
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        ExerciseTime exerciseTime = new ExerciseTime(exerciseTimeDTO.getDailyActiveTimeInMinutes(), exerciseTimeDTO.getBurntCalorie(), user);
        user.getDailyExercise().add(exerciseTime);

        //ToDo LogSave
        ExerciseTime savedExerciseTime = IExerciseTimeRepository.save(exerciseTime);
        return new ExerciseTimeDTO(savedExerciseTime.getDailyActiveTime().toMinutes(), savedExerciseTime.getBurntCalorie());
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
        return getAllExerciseTimeDTO(user);
    }
    @Override
    public List<ExerciseTimeDTO> edit(Long id, ExerciseTimeDTO exerciseTimeDTO, Authentication authentication) throws ExerciseTimeNotFoundException {
        ExerciseTime exerciseTime = IExerciseTimeRepository.findExerciseTimeById(id);
        if (exerciseTime == null) {
            throw new ExerciseTimeNotFoundException();
        }
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        exerciseTime.setDailyActiveTime(Duration.ofMinutes(exerciseTimeDTO.getDailyActiveTimeInMinutes()));
        exerciseTime.setBurntCalorie(exerciseTimeDTO.getBurntCalorie());
        IExerciseTimeRepository.save(exerciseTime);
        //ToDo LogSave
        return getAllExerciseTimeDTO(user);
    }
    @Override
    public List<ExerciseTimeDTO> getAllByAuthentication(Authentication authentication) {
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        List<ExerciseTime> userExerciseTimeList = IExerciseTimeRepository.findAllByUser(user);
        List<ExerciseTimeDTO> exerciseTimeDTOList = new ArrayList<>();

        for (ExerciseTime exerciseTime : userExerciseTimeList) {
            ExerciseTimeDTO exerciseTimeDTO = new ExerciseTimeDTO();
            exerciseTimeDTO.setBurntCalorie(exerciseTime.getBurntCalorie());
            exerciseTimeDTO.setDailyActiveTimeInMinutes(exerciseTime.getDailyActiveTime().toMinutes());
            exerciseTimeDTOList.add(exerciseTimeDTO);
        }
        return exerciseTimeDTOList;
    }
    @Override
    public List<ExerciseTimeDTO> getAllExerciseTimeDTO(User user) {
        List<ExerciseTime> userExerciseTimeList = IExerciseTimeRepository.findAllByUser(user);
        List<ExerciseTimeDTO> exerciseTimeDTOList = new ArrayList<>();

        for (ExerciseTime exerciseTime : userExerciseTimeList) {
            ExerciseTimeDTO exerciseTimeDTO = new ExerciseTimeDTO();
            exerciseTimeDTO.setBurntCalorie(exerciseTime.getBurntCalorie());
            exerciseTimeDTO.setDailyActiveTimeInMinutes(exerciseTime.getDailyActiveTime().toMinutes());
            exerciseTimeDTOList.add(exerciseTimeDTO);
        }
        return exerciseTimeDTOList;
    }
}