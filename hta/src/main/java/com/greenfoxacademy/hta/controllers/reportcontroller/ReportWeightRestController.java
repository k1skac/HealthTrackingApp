package com.greenfoxacademy.hta.controllers.reportcontroller;

import com.greenfoxacademy.hta.dtos.reportsdto.dateAndTime.RequestDaysToPeriodDTO;
import com.greenfoxacademy.hta.dtos.reportsdto.health.ResponseWeightDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.services.reportservice.healthreports.IReportWeightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/user/reports/weight")
@RequiredArgsConstructor
public class ReportWeightRestController {

    private final IReportWeightService IReportWeightService;

    @GetMapping("/weight-current")
    public ResponseEntity<?> viewCurrentWeight(Authentication authentication) {
        try {
            ResponseWeightDTO responseWeightDTOList = IReportWeightService.getCurrentWeight(
                    authentication);
            return ResponseEntity.status(200).body(responseWeightDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/weight-inPeriod")
    public ResponseEntity<?> viewWeight(Authentication authentication, @RequestBody @Valid RequestDaysToPeriodDTO requestDaysToPeriodDTO) {
        try {
            List<ResponseWeightDTO> responseWeightDTOList = IReportWeightService.getWeightInPeriod(Date.valueOf(requestDaysToPeriodDTO.getStartDate()), Date.valueOf(requestDaysToPeriodDTO.getEndDate()), authentication);
            return ResponseEntity.status(200).body(responseWeightDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }
}
