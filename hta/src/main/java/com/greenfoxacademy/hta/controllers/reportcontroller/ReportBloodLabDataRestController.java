package com.greenfoxacademy.hta.controllers.reportcontroller;

import com.greenfoxacademy.hta.dtos.reportsdto.dateAndTime.RequestDaysToPeriodDTO;
import com.greenfoxacademy.hta.dtos.reportsdto.labor.ResponseCalciumDTO;
import com.greenfoxacademy.hta.dtos.reportsdto.labor.ResponseGlucoseDTO;
import com.greenfoxacademy.hta.dtos.reportsdto.labor.ResponseLastBloodDataLabDTO;
import com.greenfoxacademy.hta.dtos.reportsdto.labor.ResponseTriGlyceridesDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.services.reportservice.bloodlabreports.IReportBloodLabDataService;
import com.greenfoxacademy.hta.services.reportservice.bloodlabreports.IReportCalciumService;
import com.greenfoxacademy.hta.services.reportservice.bloodlabreports.IReportGlucoseService;
import com.greenfoxacademy.hta.services.reportservice.bloodlabreports.IReportTriGlyceridesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/user/reports/blood-data")
@RequiredArgsConstructor
public class ReportBloodLabDataRestController {

    private final IReportBloodLabDataService IReportBloodLabDataService;
    private final IReportCalciumService IReportCalciumService;
    private final IReportGlucoseService IReportGlucoseService;
    private final IReportTriGlyceridesService IReportTriGlyceridesService;

    @GetMapping("/last-labor")
    public ResponseEntity<?> viewAllLastLabor(Authentication authentication) {
        ResponseLastBloodDataLabDTO responseLastBloodDataLabDTO = IReportBloodLabDataService.getLastAllBloodLabData(authentication);
        return ResponseEntity.status(200).body(responseLastBloodDataLabDTO);
    }

    @GetMapping("/all-calcium")
    public ResponseEntity<?> viewAllCalcium(Authentication authentication) {
        try {
            List<ResponseCalciumDTO> responseCalciumDTOList = IReportCalciumService.getAllCalciumLabor(authentication);
            return ResponseEntity.status(200).body(responseCalciumDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/period-calcium")
    public ResponseEntity<?> viewAllCalciumInPeriod(Authentication authentication,
                                                    @RequestBody @Valid RequestDaysToPeriodDTO requestDaysToPeriodDTO) {
        Date startDate = Date.valueOf(requestDaysToPeriodDTO.getStartDate());
        Date endDate = Date.valueOf(requestDaysToPeriodDTO.getEndDate());
        try {
            List<ResponseCalciumDTO> responseCalciumDTOList = IReportCalciumService.getCalciumInPeriod(authentication, startDate.toLocalDate().atStartOfDay(), endDate.toLocalDate().atTime(LocalTime.MIDNIGHT));
            return ResponseEntity.status(200).body(responseCalciumDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/lastMonth-calcium")
    public ResponseEntity<?> viewAllCalciumInLastMonth(Authentication authentication) {
        LocalDateTime startDate = LocalDateTime.now().withNano(0);
        LocalDateTime endDate = startDate.minusMonths(1);
        try {
            List<ResponseCalciumDTO> responseCalciumDTOList = IReportCalciumService.getCalciumInPeriod(authentication, endDate, startDate);
            return ResponseEntity.status(200).body(responseCalciumDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/lastSixMonth-calcium")
    public ResponseEntity<?> viewAllCalciumInLastSixMonth(Authentication authentication) {
        LocalDateTime startDate = LocalDateTime.now().withNano(0);
        LocalDateTime endDate = startDate.minusMonths(6);
        try {
            List<ResponseCalciumDTO> responseCalciumDTOList = IReportCalciumService.getCalciumInPeriod(authentication, endDate, startDate);
            return ResponseEntity.status(200).body(responseCalciumDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/some-calcium")
    public ResponseEntity<?> viewSomeCalcium(Authentication authentication, @RequestParam int neededLabor) {
        try {
            List<ResponseCalciumDTO> responseCalciumDTOList;
            responseCalciumDTOList = IReportCalciumService.getLastSomeCalciumLabor(authentication, neededLabor);
            return ResponseEntity.status(200).body(responseCalciumDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/all-glucose")
    public ResponseEntity<?> viewAllGlucose(Authentication authentication) {
        try {
            List<ResponseGlucoseDTO> responseGlucoseDTOList = IReportGlucoseService.getAllGlucoseLabor(authentication);
            return ResponseEntity.status(200).body(responseGlucoseDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/period-glucose")
    public ResponseEntity<?> viewAllGlucoseInPeriod(Authentication authentication,
                                                    @RequestBody @Valid RequestDaysToPeriodDTO requestDaysToPeriodDTO) {
        Date startDate = Date.valueOf(requestDaysToPeriodDTO.getStartDate());
        Date endDate = Date.valueOf(requestDaysToPeriodDTO.getEndDate());
        try {
            List<ResponseGlucoseDTO> responseGlucoseDTOList = IReportGlucoseService.getGlucoseInPeriod(authentication, startDate.toLocalDate().atStartOfDay(), endDate.toLocalDate().atTime(LocalTime.MIDNIGHT));
            return ResponseEntity.status(200).body(responseGlucoseDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/lastMonth-glucose")
    public ResponseEntity<?> viewAllGlucoseInLastMonth(Authentication authentication) {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusMonths(1);
        try {
            List<ResponseGlucoseDTO> responseGlucoseDTOList = IReportGlucoseService.getGlucoseInPeriod(authentication, startDate, endDate);
            return ResponseEntity.status(200).body(responseGlucoseDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/lastSixMonth-glucose")
    public ResponseEntity<?> viewAllGlucoseInLastSixMonth(Authentication authentication) {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusMonths(6);
        try {
            List<ResponseGlucoseDTO> responseGlucoseDTOList = IReportGlucoseService.getGlucoseInPeriod(authentication, startDate, endDate);
            return ResponseEntity.status(200).body(responseGlucoseDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/some-glucose")
    public ResponseEntity<?> viewSomeGlucose(Authentication authentication, @RequestParam int neededLabor) {
        try {
            List<ResponseGlucoseDTO> responseGlucoseDTOList;
            responseGlucoseDTOList = IReportGlucoseService.getLastSomeGlucoseLabor(authentication, neededLabor);
            return ResponseEntity.status(200).body(responseGlucoseDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/all-tryGlyceride")
    public ResponseEntity<?> viewAllTryGlyceride(Authentication authentication) {
        try {
            List<ResponseTriGlyceridesDTO> responseTriGlyceridesDTOList = IReportTriGlyceridesService.getAllTriGlyceridesLabor(authentication);
            return ResponseEntity.status(200).body(responseTriGlyceridesDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/period-tryGlyceride")
    public ResponseEntity<?> viewAllTryGlycerideInPeriod(Authentication authentication,
                                                    @RequestBody @Valid RequestDaysToPeriodDTO requestDaysToPeriodDTO) {
        Date startDate = Date.valueOf(requestDaysToPeriodDTO.getStartDate());
        Date endDate = Date.valueOf(requestDaysToPeriodDTO.getEndDate());
        try {
            List<ResponseTriGlyceridesDTO> responseTriGlyceridesDTOList = IReportTriGlyceridesService.getTriGlyceridesInPeriod(authentication, startDate.toLocalDate().atStartOfDay(), endDate.toLocalDate().atTime(LocalTime.MIDNIGHT));
            return ResponseEntity.status(200).body(responseTriGlyceridesDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/lastMonth-tryGlyceride")
    public ResponseEntity<?> viewAllTryGlycerideInLastMonth(Authentication authentication) {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusMonths(1);
        try {
            List<ResponseTriGlyceridesDTO> responseTriGlyceridesDTOList = IReportTriGlyceridesService.getTriGlyceridesInPeriod(authentication, startDate, endDate);
            return ResponseEntity.status(200).body(responseTriGlyceridesDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/lastSixMonth-tryGlyceride")
    public ResponseEntity<?> viewAllTryGlycerideInLastSixMonth(Authentication authentication) {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.minusMonths(6);
        try {
            List<ResponseTriGlyceridesDTO> responseTriGlyceridesDTOList = IReportTriGlyceridesService.getTriGlyceridesInPeriod(authentication, startDate, endDate);
            return ResponseEntity.status(200).body(responseTriGlyceridesDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }

    @GetMapping("/some-tryGlyceride")
    public ResponseEntity<?> viewSomeTryGlyceride(Authentication authentication, @RequestParam int neededLabor) {
        try {
            List<ResponseTriGlyceridesDTO> responseTriGlyceridesDTOList;
            responseTriGlyceridesDTOList = IReportTriGlyceridesService.getLastSomeTriGlyceridesLabor(authentication, neededLabor);
            return ResponseEntity.status(200).body(responseTriGlyceridesDTOList);
        } catch (HtaException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
        }
    }
}


