package com.greenfoxacademy.hta.services.reportservice.healthreports;

import com.greenfoxacademy.hta.dtos.reportsdto.goal.ResponseHeartRateDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import org.springframework.security.core.Authentication;

import java.sql.Date;
import java.util.List;

public interface IReportHeartRateService {
    Long getCurrentUser(Authentication authentication);
    List<ResponseHeartRateDTO> getHeartRateCurrentDay(Authentication authentication) throws HtaException;
    List<ResponseHeartRateDTO> getHeartRateOneDay(Authentication authentication, Date date) throws HtaException;
    List<ResponseHeartRateDTO> getHeartRateOneDayByHours (Date date, String startHours, String endHours, Authentication authentication) throws HtaException;
    List<ResponseHeartRateDTO> getHeartRateOnPeriod(Authentication authentication, Date startDate, Date endDate) throws HtaException;
    List<ResponseHeartRateDTO> getHeartRateOnPeriodByHours(Authentication authentication, Date startDate, Date endDate, String startHours, String endHours) throws HtaException;
    List<ResponseHeartRateDTO> getHeartRateOnLastWeek(Authentication authentication) throws HtaException;
    List<ResponseHeartRateDTO> getHeartRateOnLastWeekByHours(Authentication authentication, String startHours, String endHours) throws HtaException;
    List<ResponseHeartRateDTO> getHeartRateOnLastMonthByHours(Authentication authentication, String startHours, String endHours) throws HtaException;
    ResponseHeartRateDTO getMaxHeartRateOnDay(Authentication authentication, Date date) throws HtaException;
    List<ResponseHeartRateDTO> getMaxHeartRateByDayLastWeek(Authentication authentication) throws HtaException;
}
