package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.bloodlabdata.Glucose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlucoseRepository extends JpaRepository<Glucose, Long> {
}
