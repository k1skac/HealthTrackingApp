package com.greenfoxacademy.hta.services.city;

import com.greenfoxacademy.hta.models.user.City;
import com.greenfoxacademy.hta.repositories.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService implements ICityService{
    private final ICityRepository iCityRepository;

    @Autowired
    public CityService(ICityRepository iCityRepository) {
        this.iCityRepository = iCityRepository;
    }

    @Override
    public City saveCity(City city) {
        return iCityRepository.save(city);
    }

    @Override
    public City findByCityName(String cityName) {
        return iCityRepository.findByCityName(cityName);
    }
}
