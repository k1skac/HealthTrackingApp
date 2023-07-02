package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.user.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityRepository extends JpaRepository<City, Long> {
    City findByCityName(String cityName);

    boolean existsByCityName(String cityName);
}