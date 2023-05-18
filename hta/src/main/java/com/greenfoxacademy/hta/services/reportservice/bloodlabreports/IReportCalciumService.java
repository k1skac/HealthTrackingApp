package com.greenfoxacademy.hta.services.reportservice.bloodlabreports;

import com.greenfoxacademy.hta.dtos.reportsdto.labor.ResponseCalciumDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface IReportCalciumService {
    ResponseCalciumDTO getLastCalciumLabor(Long laborId);
    List<ResponseCalciumDTO> getLastSomeCalciumLabor(Authentication authentication, int neededLabor) throws HtaException;
    List<ResponseCalciumDTO> getAllCalciumLabor(Authentication authentication) throws HtaException;
    List<ResponseCalciumDTO> getCalciumInPeriod(Authentication authentication, LocalDateTime startDate, LocalDateTime endDate) throws HtaException;
}
