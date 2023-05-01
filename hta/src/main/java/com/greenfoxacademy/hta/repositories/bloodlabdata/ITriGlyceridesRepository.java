package com.greenfoxacademy.hta.repositories.bloodlabdata;

import com.greenfoxacademy.hta.models.bloodlabdata.TriGlycerides;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITriGlyceridesRepository extends JpaRepository<TriGlycerides, Long> {
}
