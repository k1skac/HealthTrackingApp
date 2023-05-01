package com.greenfoxacademy.hta.services.bloodlabdata;

import com.greenfoxacademy.hta.models.bloodlabdata.BloodLabData;
import org.springframework.stereotype.Service;

public interface IBloodLabDataService {
    void save(BloodLabData bloodLabData);
}
