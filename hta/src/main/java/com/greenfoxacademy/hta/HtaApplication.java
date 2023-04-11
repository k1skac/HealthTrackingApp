package com.greenfoxacademy.hta;

import com.greenfoxacademy.hta.models.BiologicalGender;
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
import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class HtaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HtaApplication.class, args);
    }

    @Bean
    CommandLineRunner run (IUserService iUserService, IRoleRepository iRoleRepository , IUserRepository iUserRepository ,
                           PasswordEncoder passwordEncoder) {
      return  args -> {
            iUserService.saveRole(new Role(RoleName.USER));
            iUserService.saveRole(new Role(RoleName.ADMIN));
            Role role = iRoleRepository.findByRoleName(RoleName.ADMIN);
            Role role2 = iRoleRepository.findByRoleName(RoleName.USER);
            addAdmin(iUserService, iUserRepository, passwordEncoder,role2,role);
            addDummyUser(iUserService, iUserRepository, passwordEncoder,role2);
       };
    }


    public void addAdmin(IUserService iUserService, IUserRepository iUserRepository ,
                         PasswordEncoder passwordEncoder, Role role2, Role role ) {
      iUserService.saverUser(new User("Admin", "admin@gmail.com", passwordEncoder.encode("adminPassword"), new ArrayList<>()));
      User user = iUserRepository.findByEmail("admin@gmail.com").orElse(null);
      user.getRoles().add(role);
      user.getRoles().add(role2);
      iUserService.saverUser(user);
    }

    public void addDummyUser(IUserService iUserService, IUserRepository iUserRepository ,
                             PasswordEncoder passwordEncoder, Role role2) {
      List<User> users = Arrays.asList(
              new User("User1", "user1@gmail.com", passwordEncoder.encode("password1"),
                      "DummyUser1", BiologicalGender.MALE, LocalDate.of(1988,1,4), 198.5),
              new User("User2", "user2@gmail.com", passwordEncoder.encode("password2"),
                      "DummyUser2", BiologicalGender.FEMALE, LocalDate.of(1986,5,6), 168.5),
              new User("User3", "user3@gmail.com", passwordEncoder.encode("password3"),
                      "DummyUser3", BiologicalGender.MALE, LocalDate.of(1976,12,21), 168.5));
      for (User userItem: users) {
        iUserService.saverUser(userItem);
        User user2 = iUserRepository.findByEmail(userItem.getEmail()).orElse(null);
        user2.getRoles().add(role2);
        iUserService.saverUser(user2);
      }
    }
}
