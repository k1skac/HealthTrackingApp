package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.HeartRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartRateRepository extends JpaRepository<HeartRate, Long> {
}
