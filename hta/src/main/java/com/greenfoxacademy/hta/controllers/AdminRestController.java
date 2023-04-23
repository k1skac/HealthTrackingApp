package com.greenfoxacademy.hta.controllers;


import com.greenfoxacademy.hta.dtos.DeleteDTO;
import com.greenfoxacademy.hta.dtos.LogDTO;
import com.greenfoxacademy.hta.dtos.LoginDto;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.exceptions.UserNotFoundException;
import com.greenfoxacademy.hta.services.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRestController {

    private final IAdminService iAdminService ;

    @PostMapping("/userpwupdate")
    public ResponseEntity<?> adminUserChangePassword(@RequestBody LoginDto loginDto) throws HtaException {
        try {
            return iAdminService.adminUserChangePassword(loginDto);
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping("/log")
    public ResponseEntity<?> adminLog(@RequestBody LogDTO logDTO) throws UserNotFoundException {
        return iAdminService.adminLog(logDTO);
    }

    @DeleteMapping("/deleteuser")
    public ResponseEntity<?> adminLog(@RequestBody DeleteDTO deleteDTO) throws UserNotFoundException {
        return iAdminService.userDelete(deleteDTO);
    }

}
