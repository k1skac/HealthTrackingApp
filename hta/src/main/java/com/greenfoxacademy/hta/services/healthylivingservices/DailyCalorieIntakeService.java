package com.greenfoxacademy.hta.services.healthylivingservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.hta.dtos.healthylivingentitydto.DailyCalorieIntakeDTO;
import com.greenfoxacademy.hta.exceptions.DailyCalorieIntakeNoContentException;
import com.greenfoxacademy.hta.exceptions.DailyCalorieIntakeNotFoundException;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.models.healthylivingentities.DailyCalorieIntake;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.repositories.healthylivingrepositories.IDailyCalorieIntakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyCalorieIntakeService implements IDailyCalorieIntakeService {
    private final IUserRepository iUserRepository;
    private final IDailyCalorieIntakeRepository IDailyCalorieIntakeRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public DailyCalorieIntakeDTO save(DailyCalorieIntakeDTO dailyCalorieIntakeDTO, Authentication authentication) throws DailyCalorieIntakeNoContentException {
        if (dailyCalorieIntakeDTO == null || dailyCalorieIntakeDTO.getCalorie() == 0){
            throw new DailyCalorieIntakeNoContentException();
        }
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        DailyCalorieIntake dailyCalorie = new DailyCalorieIntake(dailyCalorieIntakeDTO.getCalorie(),user);
        user.getDailyCalories().add(dailyCalorie);
        IDailyCalorieIntakeRepository.save(dailyCalorie);
        iUserRepository.save(user);
        //ToDo LogSave
        return objectMapper.convertValue(dailyCalorie, DailyCalorieIntakeDTO.class);
    }

    @Override
    public List<DailyCalorieIntakeDTO> delete(Long id, Authentication authentication) throws DailyCalorieIntakeNotFoundException {
        DailyCalorieIntake dailyCalorieIntake = IDailyCalorieIntakeRepository.findDailyCalorieIntakeById(id);
        if (dailyCalorieIntake == null) {
            throw new DailyCalorieIntakeNotFoundException();
        }
        IDailyCalorieIntakeRepository.delete(dailyCalorieIntake);
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        user.getDailyCalories().remove(dailyCalorieIntake);
        iUserRepository.save(user);
        //ToDo LogSave
        return getAll(user);
    }

    @Override
    public List<DailyCalorieIntakeDTO> edit(Long id, DailyCalorieIntakeDTO dailyCalorieIntakeDTO, Authentication authentication) throws DailyCalorieIntakeNotFoundException, DailyCalorieIntakeNoContentException {
        if (dailyCalorieIntakeDTO == null || dailyCalorieIntakeDTO.getCalorie() == 0){
            throw new DailyCalorieIntakeNoContentException();
        }
        DailyCalorieIntake dailyCalorieIntake = IDailyCalorieIntakeRepository.findDailyCalorieIntakeById(id);
        if (dailyCalorieIntake == null) {
            throw new DailyCalorieIntakeNotFoundException();
        }
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        dailyCalorieIntake.setCalorie(dailyCalorieIntakeDTO.getCalorie());
        IDailyCalorieIntakeRepository.save(dailyCalorieIntake);
        //ToDo LogSave
        return getAll(user);
    }

    @Override
    public List<DailyCalorieIntakeDTO> getAllByAuthentication(Authentication authentication) {
        User user = iUserRepository.findByEmail(authentication.getName()).get();
        return IDailyCalorieIntakeRepository.findAllByUser(user)
                .stream()
                .map(dailyCalorieIntake -> objectMapper.convertValue(dailyCalorieIntake, DailyCalorieIntakeDTO.class))
                .toList();
    }
    @Override
    public List<DailyCalorieIntakeDTO> getAll(User user) {
        return IDailyCalorieIntakeRepository.findAllByUser(user)
                .stream()
                .map(dailyCalorieIntake -> objectMapper.convertValue(dailyCalorieIntake, DailyCalorieIntakeDTO.class))
                .toList();
    }
}
