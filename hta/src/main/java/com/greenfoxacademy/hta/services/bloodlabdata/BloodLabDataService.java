package com.greenfoxacademy.hta.services.bloodlabdata;

import com.greenfoxacademy.hta.models.bloodlabdata.BloodLabData;
import com.greenfoxacademy.hta.repositories.bloodlabdata.IBloodLabDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BloodLabDataService implements IBloodLabDataService {
    private final IBloodLabDataRepository IBloodLabDataRepository;

    @Override
    public void save(BloodLabData bloodLabData) {
        IBloodLabDataRepository.save(bloodLabData);
    }
}
