package com.greenfoxacademy.hta.services.reportservice.bloodlabreports;

import com.greenfoxacademy.hta.dtos.reportsdto.labor.ResponseTriGlyceridesDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface IReportTriGlyceridesService {

    ResponseTriGlyceridesDTO getLastTriGlyceridesLabor(Long laborId);
    List<ResponseTriGlyceridesDTO> getLastSomeTriGlyceridesLabor(Authentication authentication, int neededLabor) throws HtaException;
    List<ResponseTriGlyceridesDTO> getAllTriGlyceridesLabor(Authentication authentication) throws HtaException;
    List<ResponseTriGlyceridesDTO> getTriGlyceridesInPeriod(Authentication authentication, LocalDateTime startDate, LocalDateTime endDate) throws HtaException;
}
