package com.greenfoxacademy.hta.repositories.healthylivingrepositories;

import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.models.user.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IWeightRepository extends JpaRepository<Weight, Long> {
    List<Weight> findAllByUser(User user);
    Weight findWeightById(Long id);
    @Query("SELECT COUNT(weight) FROM Weight weight WHERE weight.user = ?1 AND weight.weightMeasuredAt >= ?2")
    int findWeightForNotification(User user, LocalDateTime date);
}
