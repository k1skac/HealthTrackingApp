package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.models.goalentities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}


