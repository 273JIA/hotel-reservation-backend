package com.mycodework.riverstonehotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycodework.riverstonehotel.exception.RoleAlreadyExistException;
import com.mycodework.riverstonehotel.model.Role;
import com.mycodework.riverstonehotel.model.User;
import com.mycodework.riverstonehotel.service.IRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RoleControllerTests {

    private MockMvc mockMvc;

    @Mock
    private IRoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    public void testGetAllRoles() throws Exception {
        List<Role> roles = new ArrayList<>();
        when(roleService.getRoles()).thenReturn(roles);

        mockMvc.perform(get("/roles/all-roles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testCreateRole_Success() throws Exception {
        Role theRole = new Role("USER");

        mockMvc.perform(post("/roles/create-new-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(theRole)))
                .andExpect(status().isOk())
                .andExpect(content().string("New role created successfully!"));
    }

    @Test
    public void testCreateRole_RoleAlreadyExist() throws Exception {
        Role theRole = new Role("USER");

        when(roleService.createRole(any(Role.class)))
                .thenThrow(new RoleAlreadyExistException("Role USER already exists"));

        mockMvc.perform(post("/roles/create-new-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(theRole)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Role USER already exists"));
    }

    @Test
    public void testDeleteRole() throws Exception {
        Long roleId = 1L;
        doNothing().when(roleService).deleteRole(eq(roleId));

        mockMvc.perform(delete("/roles/delete/{roleId}", roleId))
                .andExpect(status().isOk());
    }

    @Test
    public void testRemoveAllUsersFromRole() throws Exception {
        Long roleId = 1L;
        Role role = new Role("USER");

        when(roleService.removeAllUsersFromRole(eq(roleId))).thenReturn(role);

        mockMvc.perform(post("/roles/remove-all-users-from-role/{roleId}", roleId))
                .andExpect(status().isOk());
    }

    @Test
    public void testRemoveUserFromRole() throws Exception {
        Long userId = 1L;
        Long roleId = 1L;
        User user = new User();

        when(roleService.removeUserFromRole(eq(userId), eq(roleId))).thenReturn(user);

        mockMvc.perform(post("/roles/remove-user-from-role")
                        .param("userId", userId.toString())
                        .param("roleId", roleId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testAssignUserToRole() throws Exception {
        Long userId = 1L;
        Long roleId = 1L;
        User user = new User();

        when(roleService.assignRoleToUser(eq(userId), eq(roleId))).thenReturn(user);

        mockMvc.perform(post("/roles/assign-user-to-role")
                        .param("userId", userId.toString())
                        .param("roleId", roleId.toString()))
                .andExpect(status().isOk());
    }

    // Utility method to convert object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
