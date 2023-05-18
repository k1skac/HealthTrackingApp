package com.greenfoxacademy.hta.controllers.reportcontroller;

import com.greenfoxacademy.hta.dtos.reportsdto.dateAndTime.*;
import com.greenfoxacademy.hta.dtos.reportsdto.goal.ResponseHeartRateDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.services.reportservice.healthreports.IReportHeartRateService;
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
@RequestMapping("/user/reports/heart")
@RequiredArgsConstructor
public class ReportHeartRestController {

    private final IReportHeartRateService IReportHeartRateService;

    @GetMapping("/heartRateToday")
    public ResponseEntity<?> getHeartRateCurrentDay(Authentication authentication) {
        try {
            List<ResponseHeartRateDTO> allHeartRateToday = IReportHeartRateService.getHeartRateCurrentDay(authentication);
            return ResponseEntity.status(200).body(allHeartRateToday);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/heartRateOnSelectedDay")
    public ResponseEntity<?> getHeartRateOnSelectedDay(Authentication authentication, @RequestBody @Valid RequestDateDTO requestDateDTO) {
        try {
            List<ResponseHeartRateDTO> allHeartRateToday = IReportHeartRateService.getHeartRateOneDay (authentication, Date.valueOf(requestDateDTO.getSelectedDate()));
            return ResponseEntity.status(200).body(allHeartRateToday);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/heartRateOnSelectedDayByHours")
    public ResponseEntity<?> getHeartRateOnSelectedDayByHours(Authentication authentication,
                                                              @RequestBody @Valid RequestDayAndHoursDTO requestDayAndHoursDTO) {
        try {
            List<ResponseHeartRateDTO> allHeartRateOnDayByHours = IReportHeartRateService.getHeartRateOneDayByHours (Date.valueOf(requestDayAndHoursDTO.getSelectedDate()), requestDayAndHoursDTO.getStartHour(), requestDayAndHoursDTO.getEndHour(), authentication);
            return ResponseEntity.status(200).body(allHeartRateOnDayByHours);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/heartRateOnPeriod")
    public ResponseEntity<?> getHeartRateOnPeriod(Authentication authentication,
                                                  @RequestBody @Valid RequestDaysToPeriodDTO requestDaysToPeriodDTO) {
        try {
            List<ResponseHeartRateDTO> allHeartRateOnPeriod = IReportHeartRateService.getHeartRateOnPeriod(authentication, Date.valueOf(requestDaysToPeriodDTO.getStartDate()), Date.valueOf(requestDaysToPeriodDTO.getEndDate()));
            return ResponseEntity.status(200).body(allHeartRateOnPeriod);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/heartRateOnPeriodByHours")
    public ResponseEntity<?> getHeartRateOnPeriodByHours(Authentication authentication,
                                                  @RequestBody @Valid RequestDaysToPeriodAndHours requestDaysToPeriodAndHours) {
        try {
            List<ResponseHeartRateDTO> allHeartRateOnPeriodByHours = IReportHeartRateService.getHeartRateOnPeriodByHours(authentication, Date.valueOf(requestDaysToPeriodAndHours.getStartDate()),
                    Date.valueOf(requestDaysToPeriodAndHours.getEndDate()), requestDaysToPeriodAndHours.getStartHour(), requestDaysToPeriodAndHours.getEndHour());
            return ResponseEntity.status(200).body(allHeartRateOnPeriodByHours);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/heartRateOnLastWeek")
    public ResponseEntity<?> getHeartRateOnLastWeek(Authentication authentication) {
        try {
            List<ResponseHeartRateDTO> allHeartRateOnLastWeek = IReportHeartRateService.getHeartRateOnLastWeek(authentication);
            return ResponseEntity.status(200).body(allHeartRateOnLastWeek);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/heartRateOnLastWeekByHours")
    public ResponseEntity<?> getHeartRateOnLastWeekByHours(Authentication authentication,
                                                           @RequestBody @Valid RequestHoursDTO requestHoursDTO) {
        try {
            List<ResponseHeartRateDTO> allHeartRateOnLastWeek = IReportHeartRateService.getHeartRateOnLastWeekByHours(authentication, requestHoursDTO.getStartHour(), requestHoursDTO.getEndHour());
            return ResponseEntity.status(200).body(allHeartRateOnLastWeek);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/heartRateOnLastMonthByHours")
    public ResponseEntity<?> getHeartRateOnLastMonthByHours(Authentication authentication,
                                                           @RequestBody @Valid RequestHoursDTO requestHoursDTO) {
        try {
            List<ResponseHeartRateDTO> allHeartRateOnLastWeek = IReportHeartRateService.getHeartRateOnLastMonthByHours(authentication, requestHoursDTO.getStartHour(), requestHoursDTO.getEndHour());
            return ResponseEntity.status(200).body(allHeartRateOnLastWeek);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/maxHeartRateOnSelectedDay")
    public ResponseEntity<?> getMaxHeartRateOnSelectedDay(Authentication authentication, @RequestBody @Valid RequestDateDTO requestDateDTO) {
        try {
            ResponseHeartRateDTO maxHeartRateOnDay = IReportHeartRateService.getMaxHeartRateOnDay (authentication, Date.valueOf(requestDateDTO.getSelectedDate()));
            return ResponseEntity.status(200).body(maxHeartRateOnDay);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/maxHeartRateOnLastWeek")
    public ResponseEntity<?> getMaxHeartRateByDayLastWeek(Authentication authentication) {
        try {
            List<ResponseHeartRateDTO> allHeartRateOnLastWeek = IReportHeartRateService.getMaxHeartRateByDayLastWeek(authentication);
            return ResponseEntity.status(200).body(allHeartRateOnLastWeek);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }
}
