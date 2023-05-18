package com.greenfoxacademy.hta.services.reportservice.bloodlabreports;

import com.greenfoxacademy.hta.dtos.reportsdto.labor.ResponseLastBloodDataLabDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import org.springframework.security.core.Authentication;

public interface IReportBloodLabDataService {

    Long getCurrentUser(Authentication authentication);

    Long getBloodLabDataId (Authentication authentication) throws HtaException;

    ResponseLastBloodDataLabDTO getLastAllBloodLabData(Authentication authentication);
}
