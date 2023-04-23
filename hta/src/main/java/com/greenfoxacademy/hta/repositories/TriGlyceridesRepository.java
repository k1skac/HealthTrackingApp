package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.bloodlabdata.TriGlycerides;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriGlyceridesRepository extends JpaRepository<TriGlycerides, Long> {
}
