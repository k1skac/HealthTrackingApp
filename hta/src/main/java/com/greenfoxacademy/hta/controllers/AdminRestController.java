package com.greenfoxacademy.hta.controllers;


import com.greenfoxacademy.hta.dtos.DeleteDTO;
import com.greenfoxacademy.hta.dtos.LogDTO;
import com.greenfoxacademy.hta.dtos.LoginDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.exceptions.UserNotFoundException;
import com.greenfoxacademy.hta.services.admin.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRestController {
    private final IAdminService iAdminService ;

    @PostMapping("/user-pw-update")
    public ResponseEntity<?> adminUserChangePassword(@RequestBody LoginDTO loginDto) {
        try {
            return new ResponseEntity<>(iAdminService.adminUserChangePassword(loginDto), HttpStatus.OK);
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
        }
    }

    @PostMapping("/log")
    public ResponseEntity<?> adminLog(@RequestBody LogDTO logDTO) {
        try {
            return ResponseEntity.ok(iAdminService.adminLog(logDTO));
        } catch (HtaException exception) {
            return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
        }
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> adminLog(@RequestBody DeleteDTO deleteDTO) {
        try{
            return ResponseEntity.ok(iAdminService.userDelete(deleteDTO));
        } catch (HtaException exception) {
            return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
        }
    }
}
