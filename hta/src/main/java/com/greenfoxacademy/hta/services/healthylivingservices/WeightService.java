package com.greenfoxacademy.hta.services.healthylivingservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.hta.dtos.saveuserdata.SaveWeightDTO;
import com.greenfoxacademy.hta.exceptions.WeightNotFoundException;
import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.models.Weight;
import com.greenfoxacademy.hta.repositories.healthylivingrepositories.WeightRepository;
import com.greenfoxacademy.hta.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WeightService implements IWeightService {
    private final IUserService iUserService;
    private final WeightRepository weightRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void save(Weight weight, Authentication authentication) {
        User user = iUserService.findByEmail(authentication.getName());
        weight.setUser(user);
        weightRepository.save(weight);
        List<Weight> weights = user.getWeights();
        weights.add(weight);
        iUserService.saveUser(user);
    }

    @Override
    public List<SaveWeightDTO> getAll(Authentication authentication) {
        User user = iUserService.findByEmail(authentication.getName());
        List<SaveWeightDTO> saveWeightDTOS = weightRepository.findAllByUser(user)
                .stream()
                .map(weight ->objectMapper.convertValue(weight, SaveWeightDTO.class))
                .toList();
        return saveWeightDTOS;
    }

    @Override
    public List<SaveWeightDTO> edit(Long id, Authentication authentication, SaveWeightDTO saveWeightDTO) throws WeightNotFoundException {
        if (!weightRepository.findById(id).isPresent()){
            throw new WeightNotFoundException("Sorry, this id does not exist!");
        }
        Weight weight = weightRepository.findWeightById(id);
        User user = iUserService.findByEmail(authentication.getName());
        weight.setWeight(saveWeightDTO.getWeight());
        weightRepository.save(weight);
        List<Weight> weights = user.getWeights();
        weights.add(weight);
        return getAll(authentication);
    }

    @Override
    public List<SaveWeightDTO> delete(Long id, Authentication authentication) throws WeightNotFoundException{
        if (!weightRepository.findById(id).isPresent()){
            throw new WeightNotFoundException("Sorry, this id does not exist!");
        }
        Weight weight = weightRepository.findWeightById(id);
        User user = iUserService.findByEmail(authentication.getName());
        weightRepository.delete(weight);
        user.getWeights().remove(weight);
        iUserService.saveUser(user);
        return getAll(authentication);
    }
}
