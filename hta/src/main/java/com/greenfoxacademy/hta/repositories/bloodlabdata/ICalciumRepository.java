package com.greenfoxacademy.hta.repositories.bloodlabdata;

import com.greenfoxacademy.hta.models.bloodlabdata.Calcium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICalciumRepository extends JpaRepository<Calcium, Long> {
}
