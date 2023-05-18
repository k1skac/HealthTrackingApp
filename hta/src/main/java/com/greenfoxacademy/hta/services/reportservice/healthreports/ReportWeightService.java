package com.greenfoxacademy.hta.services.reportservice.healthreports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.hta.dtos.reportsdto.health.ResponseWeightDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.exceptions.reportsexceptions.WeightNotFoundException;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.repositories.reportrepository.IReportWeightLossRepository;
import com.greenfoxacademy.hta.repositories.reportrepository.IReportWeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ReportWeightService implements IReportWeightService {

    private final IReportWeightRepository IReportWeightRepository;
    private final IUserRepository iUserRepository;
    private final IReportWeightLossRepository weightLostRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReportWeightService(IReportWeightRepository IReportWeightRepository, IUserRepository iUserRepository, IReportWeightLossRepository weightLostRepository, ObjectMapper objectMapper) {
        this.IReportWeightRepository = IReportWeightRepository;
        this.iUserRepository = iUserRepository;
        this.weightLostRepository = weightLostRepository;
        this.objectMapper = objectMapper;
    }

    public String calculateBMI(Authentication authentication) {
        double height = iUserRepository.findByEmail(authentication.getName()).get().getHeight();
        float weight = getCurrentUser(authentication);
        double bmi = weight / (height * height);
        if (bmi < 18.5) {
            return "Underweight";
        }
        else if (bmi < 25) {
            return "Normal";
        }
        else if (bmi < 30) {
            return "Overweight";
        }
        else {
            return "Obese";
        }

    }

    @Override
    public Long getCurrentUser(Authentication authentication) {
        return iUserRepository.findByEmail(authentication.getName()).get().getId();
    }

    @Override
    public ResponseWeightDTO getCurrentWeight(Authentication authentication) throws HtaException{
        Long userId = getCurrentUser(authentication);
        if(IReportWeightRepository.getCurrentWeight(userId) != null) {
            ResponseWeightDTO responseWeightDTO =  objectMapper.convertValue(IReportWeightRepository.getCurrentWeight(userId), ResponseWeightDTO.class);
            responseWeightDTO.setBodyMassIndex(calculateBMI(authentication));
            return responseWeightDTO;
        }
        throw new WeightNotFoundException();
    }

    @Override
    public List<ResponseWeightDTO> getWeightInPeriod(Date startDate, Date endDate, Authentication authentication) throws HtaException {
        Long userId = getCurrentUser(authentication);
        if(!IReportWeightRepository.getWeightInPeriod(startDate, endDate,userId).isEmpty()) {
            List<ResponseWeightDTO> responseWeightDTOList = IReportWeightRepository.getWeightInPeriod(startDate, endDate,userId).stream()
                    .map(weight -> objectMapper.convertValue(weight, ResponseWeightDTO.class))
                    .toList();
            for(ResponseWeightDTO responseWeightDTO : responseWeightDTOList) {
                responseWeightDTO.setBodyMassIndex(calculateBMI(authentication));
            }
             return responseWeightDTOList;
        }
        throw new WeightNotFoundException();
    }
}
