package edu.ntnu.idatt2105.placeholder.service;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ntnu.idatt2105.placeholder.exceptions.DatabaseException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.placeholder.exceptions.user.UsernameAlreadyExistsException;
import edu.ntnu.idatt2105.placeholder.model.user.Role;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import edu.ntnu.idatt2105.placeholder.repository.user.UserRepository;
import edu.ntnu.idatt2105.placeholder.service.user.UserService;
import edu.ntnu.idatt2105.placeholder.service.user.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class UserServiceIntegrationTest {

    @TestConfiguration
    static class UserServiceTestConfiguration {
        
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    UserService userService;

    @MockBean 
    UserRepository userRepository;

    User user;

    User badUser;

    @Before
    public void setUp() {
        user = new User("username", "email", "firstName", "lastName", "password", Role.USER);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        when(userRepository.save(user)).thenReturn(user);

        doNothing().when(userRepository).delete(user);

        when(userRepository.findAll()).thenReturn(List.of(user));

        badUser = new User("badUsername", "badEmail", "badFirstName", "badLastName", "badPassword", Role.USER);
        
        when(userRepository.findByUsername(badUser.getUsername())).thenReturn(Optional.empty());

        when(userRepository.findByEmail(badUser.getEmail())).thenReturn(Optional.empty());

        when(userRepository.save(badUser)).thenThrow(new RuntimeException("Username already exists"));

        doNothing().when(userRepository).delete(badUser);
    }

    @Test
    public void testSaveUser() {
        User newUser;
        try {
            newUser = userService.saveUser(user);
        } catch (Exception e) {
            fail();
            return;
        }
        
        assertEquals(user.getUsername(), newUser.getUsername());
        assertEquals(user.getEmail(), newUser.getEmail());
        assertEquals(user.getFirstName(), newUser.getFirstName());
        assertEquals(user.getLastName(), newUser.getLastName());
        assertEquals(user.getPassword(), newUser.getPassword());
        assertEquals(user.getRole(), newUser.getRole());
    }

    @Test
    public void testSaveUserBadUsername() {
        try {
            userService.saveUser(badUser);
            fail();
        } catch (Exception e) {
            assertEquals(UsernameAlreadyExistsException.class, e.getClass());
        }
    }

    @Test
    public void testUsernameExists() {
        boolean exists = userService.usernameExists(user.getUsername());
        assertTrue(exists);
    }

    @Test
    public void testUsernameExistsBadUsername() {
        boolean exists = userService.usernameExists(badUser.getUsername());
        assertFalse(exists);
    }

    @Test
    public void testEmailExists() {
        boolean exists = userService.emailExists(user.getEmail());
        assertTrue(exists);
    }

    @Test
    public void testEmailExistsBadEmail() {
        boolean exists = userService.emailExists(badUser.getEmail());
        assertFalse(exists);
    }

    @Test
    public void testGetUserByUsername() {
        User newUser;
        try {
            newUser = userService.getUserByUsername(user.getUsername());
        } catch (Exception e) {
            fail();
            return;
        }
        
        assertEquals(user.getUsername(), newUser.getUsername());
        assertEquals(user.getEmail(), newUser.getEmail());
        assertEquals(user.getFirstName(), newUser.getFirstName());
        assertEquals(user.getLastName(), newUser.getLastName());
        assertEquals(user.getPassword(), newUser.getPassword());
        assertEquals(user.getRole(), newUser.getRole());
    }

    @Test
    public void testGetUserByUsernameBadUsername() {
        try {
            userService.getUserByUsername(badUser.getUsername());
            fail();
        } catch (Exception e) {
            assertEquals(e.getClass(), UserDoesNotExistsException.class);
        }
    }

    @Test
    public void testGetUserByEmail() {
        User newUser;
        try {
            newUser = userService.getUserByEmail(user.getEmail());
        } catch (Exception e) {
            fail();
            return;
        }
        
        assertEquals(user.getUsername(), newUser.getUsername());
        assertEquals(user.getEmail(), newUser.getEmail());
        assertEquals(user.getFirstName(), newUser.getFirstName());
        assertEquals(user.getLastName(), newUser.getLastName());
        assertEquals(user.getPassword(), newUser.getPassword());
        assertEquals(user.getRole(), newUser.getRole());
    }

    @Test
    public void testGetUserByEmailBadEmail() {
        try {
            userService.getUserByEmail(badUser.getEmail());
            fail();
        } catch (Exception e) {
            assertEquals(e.getClass(), UserDoesNotExistsException.class);
        }
    }

    @Test
    public void testDeleteUser() {
        try {
            userService.deleteUser(user);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testDeleteUserByUsername() {
        try {
            userService.deleteUserByUsername(user.getUsername());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testDeleteUserByUsernameBadUsername() {
        try {
            userService.deleteUserByUsername(badUser.getUsername());
            fail();
        } catch (Exception e) {
            assertEquals(e.getClass(), UserDoesNotExistsException.class);
        }
    }

    @Test
    public void testDeleteUserByEmail() {
        try {
            userService.deleteUserByEmail(user.getEmail());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testDeleteUserByEmailBadEmail() {
        try {
            userService.deleteUserByEmail(badUser.getEmail());
            fail();
        } catch (Exception e) {
            assertEquals(e.getClass(), UserDoesNotExistsException.class);
        }
    }

    @Test
    public void testUpdateUser() {
        User newUser;
        try {
            newUser = userService.updateUser(user);
        } catch (Exception e) {
            fail();
            return;
        }
        
        assertEquals(user.getUsername(), newUser.getUsername());
        assertEquals(user.getEmail(), newUser.getEmail());
        assertEquals(user.getFirstName(), newUser.getFirstName());
        assertEquals(user.getLastName(), newUser.getLastName());
        assertEquals(user.getPassword(), newUser.getPassword());
        assertEquals(user.getRole(), newUser.getRole());
    }

    @Test
    public void testUpdateUserBadUsername() {
        assertFalse(userService.usernameExists(badUser.getUsername()));
        try {
            userService.updateUser(badUser);
            fail();
        } catch (Exception e) {
            assertEquals(UserDoesNotExistsException.class, e.getClass());
        }
    }

    @Test
    public void testGetAllUsers() {
        try {
            userService.getAllUsers();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testGetAllUsersEmpty() {
        when(userRepository.findAll()).thenReturn(new ArrayList<User>());

        try {
            userService.getAllUsers();
            fail();
        } catch (Exception e) {
            assertEquals(e.getClass(), DatabaseException.class);
        }
    }
}
