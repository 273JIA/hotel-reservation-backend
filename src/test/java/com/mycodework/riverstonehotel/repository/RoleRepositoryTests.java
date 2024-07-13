package com.mycodework.riverstonehotel.repository;

import com.mycodework.riverstonehotel.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleRepositoryTests {

    @Mock
    private RoleRepository roleRepository;

    @Test
    public void testFindByName() {
        // Arrange
        String roleName = "ROLE_USER";
        Role expectedRole = new Role(roleName);
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(expectedRole));

        // Act
        Optional<Role> roleOptional = roleRepository.findByName(roleName);

        // Assert
        assertEquals(expectedRole, roleOptional.orElse(null));
    }

    @Test
    public void testExistsByName() {
        // Arrange
        String roleName = "ROLE_ADMIN";
        when(roleRepository.existsByName(roleName)).thenReturn(true);

        // Act
        boolean roleExists = roleRepository.existsByName(roleName);

        // Assert
        assertEquals(true, roleExists);
    }
}
