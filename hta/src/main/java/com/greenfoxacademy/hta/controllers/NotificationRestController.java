package com.greenfoxacademy.hta.controllers;

import com.greenfoxacademy.hta.dtos.notificationdto.NotificationRequestDTO;
import com.greenfoxacademy.hta.dtos.notificationdto.NotificationMessageDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.services.notification.INotificationMessageService;
import com.greenfoxacademy.hta.services.notification.INotificationService;
import com.greenfoxacademy.hta.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/user/notifications")
@RequiredArgsConstructor
public class NotificationRestController {
    private final IUserService iUserService;
    private final INotificationMessageService iNotificationMessageService;
    private final INotificationService iNotificationService;

    @GetMapping("/")
    public ResponseEntity<?> notifications(Authentication authentication) {
        NotificationMessageDTO notifications = iNotificationMessageService.createNotifications(iUserService.findByEmail(authentication.getName()));
        return ResponseEntity.ok().body(Objects.requireNonNullElse(notifications, "There are no more notifications for today."));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(Authentication authentication, @RequestBody(required = false) NotificationRequestDTO notificationRequestDTO) {
        try {
            return ResponseEntity.ok().body(iNotificationService.add(iUserService.findByEmail(authentication.getName()), notificationRequestDTO));
        } catch (HtaException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(Authentication authentication, @RequestBody(required = false) NotificationRequestDTO notificationRequestDTO) {
        try {
            return ResponseEntity.ok().body(iNotificationService.update(iUserService.findByEmail(authentication.getName()), notificationRequestDTO));
        } catch (HtaException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
}
