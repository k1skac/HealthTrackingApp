package com.greenfoxacademy.hta.controllers;

import com.greenfoxacademy.hta.dtos.notifications.NotificationRequestDTO;
import com.greenfoxacademy.hta.dtos.notifications.NotificationMessageDTO;
import com.greenfoxacademy.hta.dtos.notifications.NotificationResponseDTO;
import com.greenfoxacademy.hta.services.notification.INotificationMessageService;
import com.greenfoxacademy.hta.services.notification.INotificationService;
import com.greenfoxacademy.hta.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final IUserService iUserService;
    private final INotificationMessageService iNotificationMessageService;
    private final INotificationService iNotificationService;

    @GetMapping("/")
    public ResponseEntity<?> notifications(Authentication authentication) {
        NotificationMessageDTO notifications = iNotificationMessageService.createNotifications(iUserService.findByEmail(authentication.getName()));
        if (notifications == null) {
            return ResponseEntity.status(200).body("There are no more notifications for today.");
        }
        return ResponseEntity.ok().body(notifications);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(Authentication authentication, @RequestBody(required = false) NotificationRequestDTO notificationRequestDTO) {
        try {
            NotificationResponseDTO notificationResponseDTO = iNotificationService.add(iUserService.findByEmail(authentication.getName()), notificationRequestDTO);
            return ResponseEntity.ok().body(notificationResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(Authentication authentication, @RequestBody(required = false) NotificationRequestDTO notificationRequestDTO) {
        try {
            NotificationResponseDTO notificationResponseDTO = iNotificationService.update(iUserService.findByEmail(authentication.getName()), notificationRequestDTO);
            return ResponseEntity.ok().body(notificationResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
