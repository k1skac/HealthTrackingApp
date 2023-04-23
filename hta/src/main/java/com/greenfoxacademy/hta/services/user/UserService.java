package com.greenfoxacademy.hta.services.user;

import com.greenfoxacademy.hta.dtos.BearerToken;
import com.greenfoxacademy.hta.dtos.LoginDto;
import com.greenfoxacademy.hta.dtos.RegisterDto;
import com.greenfoxacademy.hta.exceptions.UserNotFoundException;
import com.greenfoxacademy.hta.repositories.ILogRepository;
import com.greenfoxacademy.hta.repositories.ILogTypeRepository;
import com.greenfoxacademy.hta.models.*;
import com.greenfoxacademy.hta.models.roles.Role;
import com.greenfoxacademy.hta.models.roles.RoleName;
import com.greenfoxacademy.hta.repositories.IRoleRepository;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.security.JwtUtilities;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final AuthenticationManager authenticationManager ;
    private final IUserRepository iUserRepository ;
    private final IRoleRepository iRoleRepository ;
    private final ILogRepository iLogRepository ;
    private final ILogTypeRepository iLogTypeRepository ;
    private final PasswordEncoder passwordEncoder ;
    private final JwtUtilities jwtUtilities ;

    @Override
    public Role saveRole(Role role) {
        return iRoleRepository.save(role);
    }

    @Override
    public User saveUser(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return iUserRepository.findByEmail(email).orElse(null);
    }

    @Override
    public ResponseEntity<?> register(RegisterDto registerDto) {
        if (registerDto.getEmail()==null||registerDto.getEmail().equals("")) {
            return new ResponseEntity<>("Missing email address!", HttpStatus.BAD_REQUEST);
        } else{
            if(iUserRepository.existsByEmail(registerDto.getEmail()))
            { return  new ResponseEntity<>("email is already taken !", HttpStatus.BAD_REQUEST); }
             else {
                User user = new User();
                user.setEmail(registerDto.getEmail());
                user.setUsername(registerDto.getUsername());
                user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
                Role role = iRoleRepository.findByRoleName(RoleName.USER);
                user.setRoles(Collections.singletonList(role));
                user.setRealName(registerDto.getRealName());
                if (registerDto.getBiologicalGender().equals("MALE")) {
                    user.setBiologicalGender(BiologicalGender.MALE);
                } else if (registerDto.getBiologicalGender().equals("FEMALE")) {
                    user.setBiologicalGender(BiologicalGender.MALE);
                } else {
                    user.setBiologicalGender(BiologicalGender.UNDEFINED);
                }
                user.setHeight(registerDto.getHeight());
                user.setBirthDate(registerDto.getBirthDate());
                user.setActive(true);
                iUserRepository.save(user);
                newLog(iLogTypeRepository.findLogTypeByName("registration"), iUserRepository.findByEmail(registerDto.getEmail()).orElseThrow(), "");
                String token = jwtUtilities.generateToken(registerDto.getEmail(), Collections.singletonList(role.getRoleName()));
                return new ResponseEntity<>(new BearerToken(token, "Bearer "), HttpStatus.OK);
            }
        }
    }

    @Override
    public String authenticate(LoginDto loginDto) throws UserNotFoundException {
      Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = iUserRepository.findByEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        if (user.isActive()) {
          List<String> rolesNames = new ArrayList<>();
          user.getRoles().forEach(r -> rolesNames.add(r.getRoleName()));
          newLog(iLogTypeRepository.findLogTypeByName("login"), user, "");
          return jwtUtilities.generateToken(user.getUsername(), rolesNames);
        } else {
          return "The user " + loginDto.getEmail() + " is not active member.";
        }
    }

    public ResponseEntity<?> userChangePassword(String newPassword, Authentication authentication) {
        User user = iUserRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        iUserRepository.save(user);
        newLog(iLogTypeRepository.findLogTypeByName("pwchange"), user,"");
        return new ResponseEntity<>(user.getPassword(),HttpStatus.OK);
    }

    public void newLog(LogType logType, User user, String description) {iLogRepository.save(new Log(user,logType,description));
    }

}

