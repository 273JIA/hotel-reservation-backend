package com.mycodework.riverstonehotel.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    private Role role;
    private User user;

    @BeforeEach
    public void setUp() {
        role = new Role("Admin");
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
    }

    @Test
    public void testRoleConstructorAndGetters() {
        assertEquals("Admin", role.getName());
    }

    @Test
    public void testAssignRoleToUser() {
        role.assignRoleToUser(user);
        assertTrue(user.getRoles().contains(role));
        assertTrue(role.getUsers().contains(user));
    }

    @Test
    public void testRemoveUserFromRole() {
        role.assignRoleToUser(user);
        role.removeUserFromRole(user);
        assertFalse(user.getRoles().contains(role));
        assertFalse(role.getUsers().contains(user));
    }

    @Test
    public void testRemoveAllUsersFromRole() {
        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("password");

        role.assignRoleToUser(user);
        role.assignRoleToUser(user2);
        role.removeAllUsersFromRole();

        assertTrue(role.getUsers().isEmpty());
        assertFalse(user.getRoles().contains(role));
        assertFalse(user2.getRoles().contains(role));
    }

    @Test
    public void testGetName() {
        assertEquals("Admin", role.getName());

        Role emptyRole = new Role();
        assertEquals("", emptyRole.getName());
    }
}
