package com.example.swapSafe.controller;

import com.example.swapSafe.dto.LoginRequest;
import com.example.swapSafe.dto.LoginResponse;
import com.example.swapSafe.dto.RegisterRequest;
import com.example.swapSafe.dto.RegisterResponse;
import com.example.swapSafe.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import com.example.swapSafe.model.User.Role;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Set;

public class AuthControllerTest {
    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        // Arrange
        RegisterRequest request = new RegisterRequest("Godstime Onyibe", "godstime@gmail.com", "godstime@gmail.com");
        RegisterResponse expectedResponse = new RegisterResponse(1L, "Godstime Onyibe", "godstime@gmail.com");

        when(authService.registerCustomer(request)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<RegisterResponse> response = authController.register(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue()); // Correct

        assertEquals(expectedResponse, response.getBody());
        verify(authService, times(1)).registerCustomer(request);
    }

    @Test
    public void testRegisterWithExistingEmail() {
        // Arrange
        RegisterRequest request = new RegisterRequest("Onyibe Godstime", "onyibe@gmail.com", "onyibe@gmail.com");

        when(authService.registerCustomer(request))
                .thenThrow(new IllegalArgumentException("Email already exists"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            authController.register(request);
        });

        assertEquals("Email already exists", exception.getMessage());
        verify(authService, times(1)).registerCustomer(request);
    }

    @Test
    public void testLogin() {
        // Arrange
        LoginRequest request = new LoginRequest("godstime@gmail.com", "password");
        LoginResponse expectedResponse = new LoginResponse("hdgh7832jhds863kassksa", 1L,"Godstime Onyibe","godstime@gmail.com", Set.of(Role.CUSTOMER));

        when(authService.login(request)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<LoginResponse> response = authController.login(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
        verify(authService, times(1)).login(request);
    }
}
