package com.greenfoxacademy.hta.services.reportservice.healthreports;

import com.greenfoxacademy.hta.dtos.reportsdto.health.ResponseBloodPressureDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import org.springframework.security.core.Authentication;

import java.sql.Date;
import java.util.List;

public interface IReportBloodPressureService {

    Long getCurrentUser(Authentication authentication);
    List<ResponseBloodPressureDTO> getBloodPressureCurrentDay(Authentication authentication) throws HtaException;
    List<ResponseBloodPressureDTO> getBloodPressureOneDay(Authentication authentication, Date date) throws HtaException;
    List<ResponseBloodPressureDTO> getBloodPressureOneDayByHours (Date date, String startHours, String endHours, Authentication authentication) throws HtaException;
    List<ResponseBloodPressureDTO> getBloodPressureOnPeriod(Authentication authentication, Date startDate, Date endDate) throws HtaException;
    List<ResponseBloodPressureDTO> getBloodPressureOnPeriodByHours(Authentication authentication, Date startDate, Date endDate, String startHours, String endHours) throws HtaException;
    List<ResponseBloodPressureDTO> getBloodPressureOnLastWeek(Authentication authentication) throws HtaException;
    List<ResponseBloodPressureDTO> getBloodPressureOnLastWeekByHours(Authentication authentication, String startHours, String endHours) throws HtaException;
    List<ResponseBloodPressureDTO> getBloodPressureOnLastMonthByHours(Authentication authentication, String startHours, String endHours) throws HtaException;
    ResponseBloodPressureDTO getMaxBloodPressureOnDay(Authentication authentication, Date date) throws HtaException;
    List<ResponseBloodPressureDTO> getMaxBloodPressureByDayLastWeek(Authentication authentication) throws HtaException;
}
