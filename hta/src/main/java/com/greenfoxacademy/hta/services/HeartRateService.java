package com.greenfoxacademy.hta.services;

import com.greenfoxacademy.hta.models.HeartRate;
import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.repositories.HeartRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeartRateService implements IHeartRateService {
    private final HeartRateRepository heartRateRepository;
    private final IUserService iUserService;

    @Autowired
    public HeartRateService(HeartRateRepository heartRateRepository, IUserService iUserService) {
        this.heartRateRepository = heartRateRepository;
        this.iUserService = iUserService;
    }

    @Override
    public void save(HeartRate heartRate, String email) {
        User user = iUserService.findByEmail(email);
            heartRate.setUser(user);
            heartRateRepository.save(heartRate);
            iUserService.saverUser(user);
    }
}
