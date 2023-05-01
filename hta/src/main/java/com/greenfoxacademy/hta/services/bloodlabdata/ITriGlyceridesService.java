package com.greenfoxacademy.hta.services.bloodlabdata;

import com.greenfoxacademy.hta.models.bloodlabdata.TriGlycerides;
import org.springframework.stereotype.Service;

public interface ITriGlyceridesService {
    void save(TriGlycerides triGlycerides, String email);
}
