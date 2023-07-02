package com.greenfoxacademy.hta.services.healthylivingservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.hta.dtos.reportsdto.health.ResponseWeightPeriodDTO;
import com.greenfoxacademy.hta.dtos.saveuserdatadto.SaveWeightDTO;
import com.greenfoxacademy.hta.exceptions.WeightNotFoundException;
import com.greenfoxacademy.hta.exceptions.WeightBadRequestException;
import com.greenfoxacademy.hta.models.filemanagement.FileData;
import com.greenfoxacademy.hta.models.notifications.NotificationMessage;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.models.user.Weight;
import com.greenfoxacademy.hta.repositories.healthylivingrepositories.IWeightRepository;
import com.greenfoxacademy.hta.services.filemanagment.IFileDataService;
import com.greenfoxacademy.hta.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeightService implements IWeightService {
    private final IUserService iUserService;
    private final IWeightRepository iWeightRepository;
    private final ObjectMapper objectMapper;
    private final IFileDataService iFileDataService;

    @Override
    public void save(SaveWeightDTO saveWeightDTO, Authentication authentication) throws Exception {
        if (saveWeightDTO.getWeight() == 0f || saveWeightDTO.getWeightMeasuredAt() == null) {
            throw new WeightBadRequestException();
        }

        User user = iUserService.findByEmail(authentication.getName());
        Weight weight = new Weight(saveWeightDTO.getWeightMeasuredAt(), saveWeightDTO.getWeight());
        weight.setUser(user);
        iWeightRepository.save(weight);

        if (saveWeightDTO.getWeightFile() != null) {
            FileData uploadedFile = iFileDataService.uploadFileDataToDirectory(saveWeightDTO.getWeightFile());
            FileData fileData = iFileDataService.save(uploadedFile);

            weight.setFileData(fileData);
            iWeightRepository.save(weight);
        }
    }

    @Override
    public List<SaveWeightDTO> getAll(Authentication authentication) {
        User user = iUserService.findByEmail(authentication.getName());
        return iWeightRepository.findAllByUser(user)
                .stream()
                .map(weight ->objectMapper.convertValue(weight, SaveWeightDTO.class))
                .toList();
    }

    @Override
    public List<SaveWeightDTO> edit(Long id, Authentication authentication, SaveWeightDTO saveWeightDTO) throws WeightNotFoundException {
        if (iWeightRepository.findById(id).isEmpty()){
            throw new WeightNotFoundException("Sorry, this id does not exist!");
        }
        Weight weight = iWeightRepository.findWeightById(id);
        User user = iUserService.findByEmail(authentication.getName());
        weight.setWeight(saveWeightDTO.getWeight());
        iWeightRepository.save(weight);
        List<Weight> weights = user.getWeights();
        weights.add(weight);
        return getAll(authentication);
    }

    @Override
    public List<SaveWeightDTO> delete(Long id, Authentication authentication) throws WeightNotFoundException{
        if (iWeightRepository.findById(id).isEmpty()){
            throw new WeightNotFoundException("Sorry, this id does not exist!");
        }
        Weight weight = iWeightRepository.findWeightById(id);
        User user = iUserService.findByEmail(authentication.getName());
        iWeightRepository.delete(weight);
        user.getWeights().remove(weight);
        iUserService.saveUser(user);
        return getAll(authentication);
    }

    @Override
    public int getWeightForNotification(User user, LocalDateTime date) {
        return iWeightRepository.findWeightForNotification(user, date);
    }

    @Override
    public String notificationMessage(User user, LocalDateTime today) {
        if (user.getNotification().getIsWeightAlertOn()) {
            if (getWeightForNotification(user, today) < user.getNotification().getWeightMeasurementFrequency()) {
                return NotificationMessage.WEIGHT.getMessage();
            }
        }
        return null;
    }

}
