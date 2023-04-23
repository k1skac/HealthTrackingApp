package com.greenfoxacademy.hta.controllers;

import com.greenfoxacademy.hta.dtos.saveuserdata.*;
import com.greenfoxacademy.hta.models.*;
import com.greenfoxacademy.hta.models.bloodlabdata.Calcium;
import com.greenfoxacademy.hta.models.bloodlabdata.Glucose;
import com.greenfoxacademy.hta.models.bloodlabdata.TriGlycerides;
import com.greenfoxacademy.hta.services.*;
import com.greenfoxacademy.hta.services.bloodpressure.IBloodPressureService;
import com.greenfoxacademy.hta.services.healthylivingservices.IWeightService;
import com.greenfoxacademy.hta.services.heartrate.IHeartRateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/saveData")
public class DataSaveController {
    private final IGlucoseService iglucoseservice;
    private final IBloodPressureService iBloodPressureService;
    private final IHeartRateService iHeartRateService;
    private final IWeightService iWeightService;
    private final ICalciumService iCalciumService;
    private final ITriGlyceridesService iTriGlyceridesService;

    @PostMapping("/heart-rate")
    public ResponseEntity<?> heartRateSaving(@Valid @RequestBody SaveHeartRateDTO saveHeartRateDTO, Authentication authentication) {
        HeartRate heartRate = new HeartRate(saveHeartRateDTO.getHeartRate(), saveHeartRateDTO.getMeasureTime());
        iHeartRateService.save(heartRate, authentication.getName());
        return ResponseEntity.ok("Data successfully saved!");
    }

    @PostMapping("/bloodpressure")
    public ResponseEntity<?> bloodPressureSaving(@Valid @RequestBody SaveBloodPressureDTO saveBloodPressureDTO,
                                                 Authentication authentication) {
        BloodPressure bloodPressure = new BloodPressure(saveBloodPressureDTO.getSystolic(), saveBloodPressureDTO.getDiastolic(),
                saveBloodPressureDTO.getMeasureTime());
        iBloodPressureService.save(bloodPressure, authentication.getName());
        return ResponseEntity.ok("Data successfully saved!");
    }

    @PostMapping("/glucose")
    public ResponseEntity<?> glucoseSaving(@Valid @RequestBody SaveGlucoseDTO saveGlucoseDTO,
                                           Authentication authentication) {
        Glucose glucose = new Glucose(saveGlucoseDTO.getGlucoseValue(), saveGlucoseDTO.getMeasureTime());
        iglucoseservice.save(glucose, authentication.getName());
        return ResponseEntity.ok("Data successfully saved!");
    }

    @PostMapping("/weight")
    public ResponseEntity<?> weightSaving(@Valid @RequestBody SaveWeightDTO saveWeightDTO,
                                          Authentication authentication) {
        Weight weight = new Weight(saveWeightDTO.getCreationDate(),saveWeightDTO.getWeight());
        iWeightService.save(weight, authentication);
        return ResponseEntity.ok("Data successfully saved!");
    }
    @PostMapping("/calcium")
    public ResponseEntity<?> calciumSaving(@Valid @RequestBody SaveCalciumDTO saveCalciumDTO,
                                          Authentication authentication) {
        Calcium calcium = new Calcium(saveCalciumDTO.getCalciumValue(), saveCalciumDTO.getMeasureTime());
        iCalciumService.save(calcium, authentication.getName());
        return ResponseEntity.ok("Data successfully saved!");
    }

    @PostMapping("/triglycerides")
    public ResponseEntity<?> triglyceridesSaving(@Valid @RequestBody SaveTriGlyceridesDTO saveTriGlyceridesDTO,
                                           Authentication authentication) {
        TriGlycerides triGlycerides = new TriGlycerides(saveTriGlyceridesDTO.getTriGlycerides(), saveTriGlyceridesDTO.getMeasureTime());
        iTriGlyceridesService.save(triGlycerides, authentication.getName());
        return ResponseEntity.ok("Data successfully saved!");
    }
}

