package com.greenfoxacademy.hta;

import com.greenfoxacademy.hta.dtos.DeleteDTO;
import com.greenfoxacademy.hta.exceptions.HtaException;
import com.greenfoxacademy.hta.exceptions.UserNotFoundException;
import com.greenfoxacademy.hta.models.user.BiologicalGender;
import com.greenfoxacademy.hta.models.user.User;
import com.greenfoxacademy.hta.repositories.IRoleRepository;
import com.greenfoxacademy.hta.repositories.log.ILogRepository;
import com.greenfoxacademy.hta.repositories.log.ILogTypeRepository;
import com.greenfoxacademy.hta.models.log.LogType;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.security.JwtUtilities;
import com.greenfoxacademy.hta.services.admin.AdminService;
import com.greenfoxacademy.hta.services.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AdminServiceTests {

    @Test
    public void adminDeleteUserOK() throws HtaException {
        var iUserRepository = mock(IUserRepository.class);
        var iLogTypeRepository = mock(ILogTypeRepository.class);
        var authenticationManager = mock(AuthenticationManager.class);
        var iRoleRepository = mock(IRoleRepository.class);
        var iLogRepository = mock(ILogRepository.class);
        var passwordEncoder = mock(PasswordEncoder.class);
        var jwtUtilities = mock(JwtUtilities.class);
        var iUserService = new UserService(authenticationManager, iUserRepository, iRoleRepository, iLogRepository, iLogTypeRepository, passwordEncoder, jwtUtilities);
        var adminService = new AdminService(iUserRepository, iLogRepository, iLogTypeRepository, passwordEncoder, iUserService);
        User user = new User("User1", "user1@gmail.com", "password1",
                "DummyUser1", BiologicalGender.MALE, LocalDate.of(1988, 1, 4), 198.5);
        Optional<User> optionalUser = Optional.of(user);
        iUserRepository.save(user);
        when(iLogTypeRepository.findLogTypeByName("adminuserdelete")).thenReturn(new LogType("adminuserdelete", "The admin deleted the account of "));
        when(iUserRepository.findByEmail("user1@gmail.com")).thenReturn(optionalUser);
        DeleteDTO deleteDTO = new DeleteDTO("user1@gmail.com");
        String expectedMessage = "The account of " + deleteDTO.getEmail() + " has deleted.";
        assertEquals(expectedMessage, adminService.userDelete(deleteDTO));      
    }

    @Test
    public void adminDeleteUserNotRegistered() throws UserNotFoundException {
        var iUserRepository = mock(IUserRepository.class);
        var iLogTypeRepository = mock(ILogTypeRepository.class);
        var authenticationManager = mock(AuthenticationManager.class);
        var iRoleRepository = mock(IRoleRepository.class);
        var iLogRepository = mock(ILogRepository.class);
        var passwordEncoder = mock(PasswordEncoder.class);
        var jwtUtilities = mock(JwtUtilities.class);
        var iUserService = new UserService(authenticationManager, iUserRepository, iRoleRepository, iLogRepository, iLogTypeRepository, passwordEncoder, jwtUtilities);
        var adminService = new AdminService(iUserRepository, iLogRepository, iLogTypeRepository, passwordEncoder, iUserService);
        User user = new User("User1", "user1@gmail.com", "password1",
                "DummyUser1", BiologicalGender.MALE, LocalDate.of(1988, 1, 4), 198.5);
        Optional<User> optionalUser = Optional.of(user);
        iUserRepository.save(user);
        when(iUserRepository.findByEmail("user1@gmail.com")).thenReturn(optionalUser);
        DeleteDTO deleteDTO = new DeleteDTO("user2@gmail.com");
        String givenEmail = "user2@gmail.com";
        System.out.println(iUserRepository.findByEmail(givenEmail).toString());
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            adminService.userDelete(deleteDTO);
        });
        String expectedMessage2 = "The user is not registered yet";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage2));
    }
}
