package com.greenfoxacademy.hta.controllers.healthylivingcontroller;

import com.greenfoxacademy.hta.dtos.saveuserdata.SaveWeightDTO;
import com.greenfoxacademy.hta.exceptions.WeightNotFoundException;
import com.greenfoxacademy.hta.services.healthylivingservices.WeightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/healthyliving")
public class HealthyLivingRestController {

    private final WeightService weightService;

    @GetMapping("/weight")
    public ResponseEntity<?> showAll(Authentication authentication) {
        return ResponseEntity.ok(weightService.getAll(authentication));
    }

    @PutMapping("/weight/{id}")
    public ResponseEntity<?> updateWeight(@PathVariable(name = "id") Long id,
                                          Authentication authentication,
                                          @RequestBody SaveWeightDTO saveWeightDTO) {
        try {
            return ResponseEntity.ok(weightService.edit(id, authentication, saveWeightDTO));
        } catch (WeightNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @DeleteMapping("/weight/{id}")
    public ResponseEntity<?> deleteWeight(@PathVariable(name = "id") Long id,
                                          Authentication authentication) {
        try {
            return ResponseEntity.ok(weightService.delete(id, authentication));
        } catch (WeightNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
