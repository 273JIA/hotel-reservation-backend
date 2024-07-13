package com.mycodework.riverstonehotel.service;

import com.mycodework.riverstonehotel.model.Role;
import com.mycodework.riverstonehotel.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoleServiceTests {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRoles() {
        // Mock data
        Role role1 = new Role("ADMIN");
        Role role2 = new Role("USER");
        List<Role> mockRoles = Arrays.asList(role1, role2);

        // Mock the repository method call
        when(roleRepository.findAll()).thenReturn(mockRoles);

        // Call the service method
        List<Role> returnedRoles = roleService.getRoles();

        // Verify the result
        assertEquals(2, returnedRoles.size());
        assertEquals("ADMIN", returnedRoles.get(0).getName());
        assertEquals("USER", returnedRoles.get(1).getName());

        // Verify that repository method was called once
        verify(roleRepository, times(1)).findAll();
    }
}
