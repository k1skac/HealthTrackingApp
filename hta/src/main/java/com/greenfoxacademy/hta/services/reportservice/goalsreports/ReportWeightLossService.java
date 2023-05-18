package com.greenfoxacademy.hta.services.reportservice.goalsreports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.hta.dtos.reportsdto.goal.ResponseWeightLostDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.models.goalentities.WeightLoss;
import com.greenfoxacademy.hta.exceptions.reportsexceptions.WeightLossGoalNotFoundException;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.repositories.reportrepository.IReportGoalRepository;
import com.greenfoxacademy.hta.services.reportservice.healthreports.IReportWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportWeightLossService implements IReportWeightLossService {

    private final IReportGoalRepository IReportGoalRepository;
    private final IUserRepository iUserRepository;
    private final IReportWeightService IReportWeightService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReportWeightLossService(IReportGoalRepository IReportGoalRepository, IUserRepository iUserRepository, IReportWeightService IReportWeightService, ObjectMapper objectMapper) {
        this.IReportGoalRepository = IReportGoalRepository;
        this.iUserRepository = iUserRepository;
        this.IReportWeightService = IReportWeightService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Long getCurrentUser(Authentication authentication) {
        return iUserRepository.findByEmail(authentication.getName()).get().getId();
    }

    @Override
    public ResponseWeightLostDTO getLastExistingWeightLostGoal(Authentication authentication) throws HtaException {
        Long userId = getCurrentUser(authentication);
        Optional<WeightLoss> lastExistingWeightLostGoal = IReportGoalRepository.getLastWeightLostGoal(userId);
        if (lastExistingWeightLostGoal.isPresent()) {
            return objectMapper.convertValue(lastExistingWeightLostGoal, ResponseWeightLostDTO.class);

        }
        throw new WeightLossGoalNotFoundException();
    }

}
