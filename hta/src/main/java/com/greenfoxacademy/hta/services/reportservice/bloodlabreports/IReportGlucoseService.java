package com.greenfoxacademy.hta.services.reportservice.bloodlabreports;

import com.greenfoxacademy.hta.dtos.reportsdto.labor.ResponseGlucoseDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface IReportGlucoseService {

    ResponseGlucoseDTO getLastGlucoseLabor(Long laborId);

    List<ResponseGlucoseDTO> getLastSomeGlucoseLabor(Authentication authentication, int neededLabor) throws HtaException;
    List<ResponseGlucoseDTO> getAllGlucoseLabor(Authentication authentication) throws HtaException;
    List<ResponseGlucoseDTO> getGlucoseInPeriod(Authentication authentication, LocalDateTime startDate, LocalDateTime endDate) throws HtaException;
}
