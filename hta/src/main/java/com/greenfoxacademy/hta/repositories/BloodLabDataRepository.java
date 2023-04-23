package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.bloodlabdata.BloodLabData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodLabDataRepository extends JpaRepository<BloodLabData, Long> {
}
