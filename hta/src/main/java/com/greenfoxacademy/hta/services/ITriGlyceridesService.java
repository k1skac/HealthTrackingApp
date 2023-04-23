package com.greenfoxacademy.hta.services;

import com.greenfoxacademy.hta.models.bloodlabdata.TriGlycerides;
import org.springframework.stereotype.Service;

@Service
public interface ITriGlyceridesService {
    void save(TriGlycerides triGlycerides, String email);
}
