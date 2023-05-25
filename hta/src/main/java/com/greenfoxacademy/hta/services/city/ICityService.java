package com.greenfoxacademy.hta.services.city;

import com.greenfoxacademy.hta.models.user.City;

public interface ICityService {
    City saveCity(City city);
    City findByCityName(String cityName);
}
