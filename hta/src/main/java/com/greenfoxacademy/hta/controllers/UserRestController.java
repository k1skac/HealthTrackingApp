package com.greenfoxacademy.hta.controllers;

import com.greenfoxacademy.hta.dtos.LoginDTO;
import com.greenfoxacademy.hta.dtos.NewPWDTO;
import com.greenfoxacademy.hta.dtos.RegisterDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.exceptions.UserNotFoundException;
import com.greenfoxacademy.hta.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {
    private final IUserService iUserService ;

    @GetMapping("/hello")
    public String sayHello (Authentication authentication) {
        return "Hello " + authentication.getName();
    }

    @GetMapping("/hello-user")
    public String sayHelloUser () {
        return "Hello User" ;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterDTO registerDto) {
        try {
            return ResponseEntity.ok(iUserService.register(registerDto));
        } catch (HtaException exception) {
            return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginDTO loginDto) throws UserNotFoundException {
        ResponseCookie jwtHTATokenCookie = ResponseCookie.from("jwtHTATokenCookie", iUserService.authenticate(loginDto)).httpOnly(true).path("/").maxAge(60*60*10).secure(true).sameSite("None").build();
        System.out.println("This is your cookie: " + jwtHTATokenCookie);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtHTATokenCookie.toString()).body(null);
    }

    @PostMapping("/pw-update")
    public ResponseEntity<?> changePassword(@RequestBody NewPWDTO newPWDTO, Authentication authentication) {
        try {
            return ResponseEntity.ok(iUserService.userChangePassword(newPWDTO.getNewPassword(), authentication));
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
        }
    }
}
