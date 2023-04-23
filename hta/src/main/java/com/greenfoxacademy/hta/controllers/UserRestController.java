package com.greenfoxacademy.hta.controllers;

import com.greenfoxacademy.hta.dtos.LoginDto;
import com.greenfoxacademy.hta.dtos.RegisterDto;
import com.greenfoxacademy.hta.services.user.IUserService;
import lombok.RequiredArgsConstructor;
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

    //ResourceEndPoint:http://localhost:8080/api/user/register
    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterDto registerDto) {
        return iUserService.register(registerDto);
    }

    //ResourceEndPoint:http://localhost:8080/api/user/authenticate
    @PostMapping("/authenticate")
    public String authenticate(@RequestBody LoginDto loginDto) {
        //return "redirect:/hello";
        return  iUserService.authenticate(loginDto);
    }
}
