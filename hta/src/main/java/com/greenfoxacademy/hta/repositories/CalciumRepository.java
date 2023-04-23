package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.bloodlabdata.Calcium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalciumRepository extends JpaRepository<Calcium, Long> {
}
