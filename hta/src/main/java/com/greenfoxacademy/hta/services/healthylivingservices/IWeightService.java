package com.greenfoxacademy.hta.services.healthylivingservices;

import com.greenfoxacademy.hta.dtos.saveuserdata.SaveWeightDTO;
import com.greenfoxacademy.hta.exceptions.WeightNotFoundException;
import com.greenfoxacademy.hta.models.Weight;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IWeightService {
    void save(Weight weight, Authentication authentication);
    List<SaveWeightDTO> getAll(Authentication authentication);
    List<SaveWeightDTO> edit(Long id, Authentication authentication, SaveWeightDTO saveWeightDTO) throws WeightNotFoundException;
    List<SaveWeightDTO> delete(Long id, Authentication authentication) throws WeightNotFoundException;
}
