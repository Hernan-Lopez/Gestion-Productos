package com.hernan.gestionproductos.controller;

import com.hernan.gestionproductos.entity.UserEntity;
import com.hernan.gestionproductos.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        // Datos de prueba
        String username = "testUser";
        String password = "testPassword";
        String role = "ADMIN";

        UserEntity mockUser = new UserEntity();
        mockUser.setUsername(username);
        mockUser.setPassword(password);
        mockUser.setRole(role);

        when(userService.createUser(username, password, role)).thenReturn(mockUser);

        UserEntity result = userController.registerUser(username, password, role);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(password, result.getPassword());
        assertEquals(role, result.getRole());

        verify(userService, times(1)).createUser(username, password, role);
    }

    @Test
    void testGetAllUsers() {
        UserEntity user1 = new UserEntity();
        user1.setUsername("user1");
        user1.setRole("USER");

        UserEntity user2 = new UserEntity();
        user2.setUsername("user2");
        user2.setRole("ADMIN");

        List<UserEntity> mockUsers = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(mockUsers);

        List<UserEntity> result = userController.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("ADMIN", result.get(1).getRole());

        verify(userService, times(1)).getAllUsers();
    }
}
