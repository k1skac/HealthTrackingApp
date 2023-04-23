package com.greenfoxacademy.hta;

import com.greenfoxacademy.hta.dtos.DeleteDTO;
import com.greenfoxacademy.hta.exceptions.UserNotFoundException;
import com.greenfoxacademy.hta.models.BiologicalGender;
import com.greenfoxacademy.hta.models.User;
import com.greenfoxacademy.hta.repositories.ILogTypeRepository;
import com.greenfoxacademy.hta.repositories.IUserRepository;
import com.greenfoxacademy.hta.services.AdminService;
import com.greenfoxacademy.hta.services.user.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTests {

    @Mock
    IUserRepository iUserRepository;
    @Mock
    ILogTypeRepository iLogTypeRepository;

    @InjectMocks
    AdminService adminService;

    @Mock
    IUserService iUserService;

    @Test
    public void adminDeleteUserOK() throws UserNotFoundException {
        User user = new User("User1", "user1@gmail.com", "password1",
                "DummyUser1", BiologicalGender.MALE, LocalDate.of(1988,1,4), 198.5);
        Optional<User> optionalUser = Optional.of(user);
        iUserRepository.save(user);
        when(iUserRepository.findByEmail("user1@gmail.com")).thenReturn(optionalUser);
        DeleteDTO deleteDTO = new DeleteDTO("user1@gmail.com");
        String givenEmail = "user1@gmail.com";
        ResponseEntity<?> expectedMessage = new ResponseEntity<>("The account of " + givenEmail + " has deleted." , HttpStatus.OK);
        assertEquals(expectedMessage, adminService.userDelete(deleteDTO));
    }

    @Test
    public void adminDeleteUserNotRegistered() throws UserNotFoundException {
        User user = new User("User1", "user1@gmail.com", "password1",
                "DummyUser1", BiologicalGender.MALE, LocalDate.of(1988,1,4), 198.5);
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
