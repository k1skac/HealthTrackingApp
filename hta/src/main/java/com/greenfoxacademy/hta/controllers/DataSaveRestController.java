package com.greenfoxacademy.hta.controllers;

import com.greenfoxacademy.hta.dtos.saveuserdatadto.*;
import com.greenfoxacademy.hta.models.bloodlabdata.Calcium;
import com.greenfoxacademy.hta.models.bloodlabdata.Glucose;
import com.greenfoxacademy.hta.models.bloodlabdata.TriGlycerides;
import com.greenfoxacademy.hta.models.user.BloodPressure;
import com.greenfoxacademy.hta.models.user.HeartRate;
import com.greenfoxacademy.hta.models.user.Weight;
import com.greenfoxacademy.hta.services.bloodlabdata.ICalciumService;
import com.greenfoxacademy.hta.services.bloodlabdata.IGlucoseService;
import com.greenfoxacademy.hta.services.bloodlabdata.ITriGlyceridesService;
import com.greenfoxacademy.hta.services.bloodpressure.IBloodPressureService;
import com.greenfoxacademy.hta.services.healthylivingservices.IWeightService;
import com.greenfoxacademy.hta.services.heartrate.IHeartRateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/save-data")
public class DataSaveRestController {
    private final IGlucoseService iglucoseservice;
    private final IBloodPressureService iBloodPressureService;
    private final IHeartRateService iHeartRateService;
    private final IWeightService iWeightService;
    private final ICalciumService iCalciumService;
    private final ITriGlyceridesService iTriGlyceridesService;
    private static final String MESSAGE = "Data successfully saved!";

    @PostMapping("/heart-rate")
    public ResponseEntity<?> heartRateSaving(@Valid @RequestBody SaveHeartRateDTO saveHeartRateDTO, Authentication authentication) {
        HeartRate heartRate = new HeartRate(saveHeartRateDTO.getHeartRate(), saveHeartRateDTO.getHeartRateMeasuredAt());
        iHeartRateService.save(heartRate, authentication.getName());
        return ResponseEntity.ok(MESSAGE);
    }

    @PostMapping("/blood-pressure")
    public ResponseEntity<?> bloodPressureSaving(@Valid @RequestBody SaveBloodPressureDTO saveBloodPressureDTO,
                                                 Authentication authentication) {
        BloodPressure bloodPressure = new BloodPressure(saveBloodPressureDTO.getSystolic(), saveBloodPressureDTO.getDiastolic(),
                saveBloodPressureDTO.getBloodPressureMeasuredAt());
        iBloodPressureService.save(bloodPressure, authentication.getName());
        return ResponseEntity.ok(MESSAGE);
    }

    @PostMapping("/glucose")
    public ResponseEntity<?> glucoseSaving(@Valid @RequestBody SaveGlucoseDTO saveGlucoseDTO, Authentication authentication) {
        Glucose glucose = new Glucose(saveGlucoseDTO.getGlucoseValue(), saveGlucoseDTO.getMeasureTime());
        iglucoseservice.save(glucose, authentication.getName());
        return ResponseEntity.ok(MESSAGE);
    }

    @PostMapping("/weight")
    public ResponseEntity<?> weightSaving(@Valid @RequestBody SaveWeightDTO saveWeightDTO, Authentication authentication) {
        Weight weight = new Weight(saveWeightDTO.getWeightMeasuredAt(),saveWeightDTO.getWeight());
        iWeightService.save(weight, authentication);
        return ResponseEntity.ok(MESSAGE);
    }

    @PostMapping("/calcium")
    public ResponseEntity<?> calciumSaving(@Valid @RequestBody SaveCalciumDTO saveCalciumDTO, Authentication authentication) {
        Calcium calcium = new Calcium(saveCalciumDTO.getCalciumValue(), saveCalciumDTO.getMeasureTime());
        iCalciumService.save(calcium, authentication.getName());
        return ResponseEntity.ok(MESSAGE);
    }

    @PostMapping("/triglycerides")
    public ResponseEntity<?> triglyceridesSaving(@Valid @RequestBody SaveTriGlyceridesDTO saveTriGlyceridesDTO,
                                           Authentication authentication) {
        TriGlycerides triGlycerides = new TriGlycerides(saveTriGlyceridesDTO.getTriGlycerides(), saveTriGlyceridesDTO.getMeasureTime());
        iTriGlyceridesService.save(triGlycerides, authentication.getName());
        return ResponseEntity.ok(MESSAGE);
    }
}

