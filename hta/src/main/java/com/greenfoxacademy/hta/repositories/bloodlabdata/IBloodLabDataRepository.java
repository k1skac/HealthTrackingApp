package com.greenfoxacademy.hta.repositories.bloodlabdata;

import com.greenfoxacademy.hta.models.bloodlabdata.BloodLabData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBloodLabDataRepository extends JpaRepository<BloodLabData, Long> {
}
