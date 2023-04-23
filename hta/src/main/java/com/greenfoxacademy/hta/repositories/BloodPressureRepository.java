package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.BloodPressure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodPressureRepository extends JpaRepository<BloodPressure, Long> {
}
