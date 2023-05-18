package com.greenfoxacademy.hta.services.reportservice.goalsreports;

import com.greenfoxacademy.hta.dtos.reportsdto.goal.ResponseWeightLostDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import org.springframework.security.core.Authentication;


public interface IReportWeightLossService {
    Long getCurrentUser(Authentication authentication);
    ResponseWeightLostDTO getLastExistingWeightLostGoal(Authentication authentication) throws HtaException;

}
