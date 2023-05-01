package com.greenfoxacademy.hta.repositories.bloodlabdata;

import com.greenfoxacademy.hta.models.bloodlabdata.Glucose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGlucoseRepository extends JpaRepository<Glucose, Long> {
}
