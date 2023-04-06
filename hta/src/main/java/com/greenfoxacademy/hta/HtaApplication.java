package com.greenfoxacademy.hta;

import com.greenfoxacademy.hta.models.Role;
import com.greenfoxacademy.hta.models.RoleName;
import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.repositories.IRoleRepository;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.services.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class HtaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HtaApplication.class, args);
    }

    @Bean
    CommandLineRunner run (IUserService iUserService, IRoleRepository iRoleRepository , IUserRepository iUserRepository , PasswordEncoder passwordEncoder) {
        return  args -> {
            iUserService.saveRole(new Role(RoleName.USER));
            iUserService.saveRole(new Role(RoleName.ADMIN));
            iUserService.saverUser(new User("admin@gmail.com", passwordEncoder.encode("adminPassword"), new ArrayList<>()));

            Role role = iRoleRepository.findByRoleName(RoleName.ADMIN);
            Role role2 = iRoleRepository.findByRoleName(RoleName.USER);
            User user = iUserRepository.findByEmail("admin@gmail.com").orElse(null);
            user.getRoles().add(role);
            user.getRoles().add(role2);
            iUserService.saverUser(user);
        };
    }
}
