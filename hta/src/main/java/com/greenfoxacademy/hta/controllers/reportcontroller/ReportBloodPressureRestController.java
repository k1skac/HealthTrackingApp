package com.greenfoxacademy.hta.controllers.reportcontroller;

import com.greenfoxacademy.hta.dtos.reportsdto.dateAndTime.*;
import com.greenfoxacademy.hta.dtos.reportsdto.health.ResponseBloodPressureDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.services.reportservice.healthreports.IReportBloodPressureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/user/reports/blp")
@RequiredArgsConstructor
public class ReportBloodPressureRestController {

    private final IReportBloodPressureService bloodPressureService;

    @GetMapping("/blpToday")
    public ResponseEntity<?> getBloodPressureCurrentDay(Authentication authentication) {
        try {
            List<ResponseBloodPressureDTO> responseBloodPressureDTOList = bloodPressureService.getBloodPressureCurrentDay(authentication);
            return ResponseEntity.status(200).body(responseBloodPressureDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/bloSelectedDay")
    public ResponseEntity<?> getBloodPressureSelectedDay(Authentication authentication, @RequestBody @Valid RequestDateDTO requestDateDTO) {
        try {
            List<ResponseBloodPressureDTO> responseBloodPressureDTOList = bloodPressureService.getBloodPressureOneDay (authentication, Date.valueOf(requestDateDTO.getSelectedDate()));
            return ResponseEntity.status(200).body(responseBloodPressureDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/blpSelectedDayByHours")
    public ResponseEntity<?> getBloodPressureSelectedDayByHours(Authentication authentication,
                                                              @RequestBody @Valid RequestDayAndHoursDTO requestDayAndHoursDTO) {
        try {
            List<ResponseBloodPressureDTO> responseBloodPressureDTOList = bloodPressureService.getBloodPressureOneDayByHours (Date.valueOf(requestDayAndHoursDTO.getSelectedDate()), requestDayAndHoursDTO.getStartHour(), requestDayAndHoursDTO.getEndHour(), authentication);
            return ResponseEntity.status(200).body(responseBloodPressureDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("blpOnPeriod")
    public ResponseEntity<?> getBloodPressureOnPeriod(Authentication authentication,
                                                  @RequestBody @Valid RequestDaysToPeriodDTO requestDaysToPeriodDTO) {
        try {
            List<ResponseBloodPressureDTO> responseBloodPressureDTOList = bloodPressureService.getBloodPressureOnPeriod(authentication, Date.valueOf(requestDaysToPeriodDTO.getStartDate()), Date.valueOf(requestDaysToPeriodDTO.getEndDate()));
            return ResponseEntity.status(200).body(responseBloodPressureDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/blpOnPeriodByHours")
    public ResponseEntity<?> getBloodPressureOnPeriodByHours(Authentication authentication,
                                                         @RequestBody @Valid RequestDaysToPeriodAndHours requestDaysToPeriodAndHours) {
        try {
            List<ResponseBloodPressureDTO> responseBloodPressureDTOList = bloodPressureService.getBloodPressureOnPeriodByHours(authentication, Date.valueOf(requestDaysToPeriodAndHours.getStartDate()),
                    Date.valueOf(requestDaysToPeriodAndHours.getEndDate()), requestDaysToPeriodAndHours.getStartHour(), requestDaysToPeriodAndHours.getEndHour());
            return ResponseEntity.status(200).body(responseBloodPressureDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/blpOnLastWeek")
    public ResponseEntity<?> getBloodPressureOnLastWeek(Authentication authentication) {
        try {
            List<ResponseBloodPressureDTO> responseBloodPressureDTOList = bloodPressureService.getBloodPressureOnLastWeek(authentication);
            return ResponseEntity.status(200).body(responseBloodPressureDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/blpOnLastWeekByHours")
    public ResponseEntity<?> getBloodPressureOnLastWeekByHours(Authentication authentication,
                                                           @RequestBody @Valid RequestHoursDTO requestHoursDTO) {
        try {
            List<ResponseBloodPressureDTO> responseBloodPressureDTOList = bloodPressureService.getBloodPressureOnLastWeekByHours(authentication, requestHoursDTO.getStartHour(), requestHoursDTO.getEndHour());
            return ResponseEntity.status(200).body(responseBloodPressureDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/blpOnLastMonthByHours")
    public ResponseEntity<?> getBloodPressureOnLastMonthByHours(Authentication authentication,
                                                            @RequestBody @Valid RequestHoursDTO requestHoursDTO) {
        try {
            List<ResponseBloodPressureDTO> responseBloodPressureDTOList = bloodPressureService.getBloodPressureOnLastMonthByHours(authentication, requestHoursDTO.getStartHour(), requestHoursDTO.getEndHour());
            return ResponseEntity.status(200).body(responseBloodPressureDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/blpMaxOnSelectedDay")
    public ResponseEntity<?> getMaxBloodPressureOnSelectedDay(Authentication authentication, @RequestBody @Valid RequestDateDTO requestDateDTO) {
        try {
            ResponseBloodPressureDTO responseBloodPressureDTO = bloodPressureService.getMaxBloodPressureOnDay (authentication, Date.valueOf(requestDateDTO.getSelectedDate()));
            return ResponseEntity.status(200).body(responseBloodPressureDTO);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }
    @GetMapping("/blpMaxOnLastWeek")
    public ResponseEntity<?> geBloodPressureByDayLastWeek(Authentication authentication) {
        try {
            List<ResponseBloodPressureDTO> responseBloodPressureDTOList = bloodPressureService.getMaxBloodPressureByDayLastWeek(authentication);
            return ResponseEntity.status(200).body(responseBloodPressureDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }
}
