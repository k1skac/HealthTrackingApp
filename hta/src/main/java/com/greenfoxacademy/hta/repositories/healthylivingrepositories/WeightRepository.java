package com.greenfoxacademy.hta.repositories.healthylivingrepositories;

import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.models.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeightRepository extends JpaRepository<Weight, Long> {
    List<Weight> findAllByUser(User user);
    Weight findWeightById(Long id);
}
