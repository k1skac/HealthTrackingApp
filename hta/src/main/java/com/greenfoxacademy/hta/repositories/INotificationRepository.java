package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.notifications.Notification;
import com.greenfoxacademy.hta.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface INotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Notification> findByUser(User user);
}
