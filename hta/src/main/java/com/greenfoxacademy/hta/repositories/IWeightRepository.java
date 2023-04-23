package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.models.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface IWeightRepository extends JpaRepository<Weight, Long> {
    @Query("SELECT COUNT(weight) FROM Weight weight WHERE weight.user = ?1 AND weight.measureTime >= ?2")
    int findWeightForNotification(User user, LocalDateTime date);
}
