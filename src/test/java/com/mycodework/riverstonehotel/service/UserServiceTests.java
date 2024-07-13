package com.mycodework.riverstonehotel.service;

import com.mycodework.riverstonehotel.exception.UserAlreadyExistsException;
import com.mycodework.riverstonehotel.model.Role;
import com.mycodework.riverstonehotel.model.User;
import com.mycodework.riverstonehotel.repository.RoleRepository;
import com.mycodework.riverstonehotel.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        // Mock data
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        Role role = new Role("ROLE_USER");
        Optional<Role> roleOptional = Optional.of(role);

        // Mock repository and encoder behavior
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(roleOptional);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Call the service method
        User registeredUser = userService.registerUser(user);

        // Verify the result
        assertNotNull(registeredUser);
        assertEquals(user.getEmail(), registeredUser.getEmail());
        assertEquals("encodedPassword", registeredUser.getPassword());
        assertEquals(1, registeredUser.getRoles().size());
        assertTrue(registeredUser.getRoles().contains(role));

        // Verify that save method was called once
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() {
        // Mock data
        User existingUser = new User();
        existingUser.setEmail("test@example.com");

        // Mock repository behavior
        when(userRepository.existsByEmail(existingUser.getEmail())).thenReturn(true);

        // Call the service method and assert exception
        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(existingUser));

        // Verify that existsByEmail method was called once
        verify(userRepository, times(1)).existsByEmail(existingUser.getEmail());
        // Verify that save method was not called
        verify(userRepository, never()).save(any(User.class));
    }
}
