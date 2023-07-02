package com.greenfoxacademy.hta.services.reportservice.healthreports;

import com.greenfoxacademy.hta.dtos.reportsdto.health.ResponseWeightDTO;
import com.greenfoxacademy.hta.dtos.reportsdto.health.ResponseWeightPeriodDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import org.springframework.security.core.Authentication;

import java.sql.Date;
import java.util.List;

public interface IReportWeightService {

    Long getCurrentUser(Authentication authentication);
    ResponseWeightDTO getCurrentWeight(Authentication authentication) throws HtaException;
    List<ResponseWeightDTO> getWeightInPeriod(Date startDate, Date endDate, Authentication authentication) throws HtaException;
    List<ResponseWeightPeriodDTO> getWeightPeriod(Date startDate, Date endDate, Authentication authentication) throws HtaException;
    List<ResponseWeightPeriodDTO> getLastSevenWeights(Authentication authentication) throws HtaException;
}
