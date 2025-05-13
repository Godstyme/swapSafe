package com.example.swapSafe.controller;

import com.example.swapSafe.controller.WalletLinkController;
import com.example.swapSafe.dto.LinkWalletRequest;
import com.example.swapSafe.security.JwtUtil;
import com.example.swapSafe.service.LinkedWalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WalletLinkControllerTest {

    private LinkedWalletService linkedWalletService;
    private JwtUtil jwtUtil;
    private WalletLinkController controller;

    @BeforeEach
    void setUp() {
        linkedWalletService = mock(LinkedWalletService.class);
        jwtUtil = mock(JwtUtil.class);
        controller = new WalletLinkController(linkedWalletService, jwtUtil);
    }

    @Test
    void testLinkWallet_Success() {
        // Arrange
        String token = "Bearer valid.jwt.token";
        String jwtToken = "valid.jwt.token";
        Long userId = 1L;
        LinkWalletRequest request = new LinkWalletRequest();
        request.setNetwork("Ethereum");
        request.setWalletAddress("0x123abc");

        when(jwtUtil.extractUserIdFromToken(jwtToken)).thenReturn(userId);

        // Act
        ResponseEntity<?> response = controller.linkWallet(request, token);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Wallet linked successfully", response.getBody());
        verify(linkedWalletService, times(1))
                .linkWallet("Ethereum", "0x123abc", 1L);
    }

    @Test
    void testLinkWallet_MissingToken() {
        // Act
        ResponseEntity<?> response = controller.linkWallet(
                new LinkWalletRequest(), null);


        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Token is missing or invalid", response.getBody());
        verify(linkedWalletService, never()).linkWallet(any(), any(), any());
    }

    @Test
    void testLinkWallet_InvalidRequest() {
        // Arrange
        LinkWalletRequest invalidRequest = new LinkWalletRequest();
        String token = "Bearer valid.jwt.token";

        // Act
        ResponseEntity<?> response = controller.linkWallet(invalidRequest, token);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Network and wallet address must not be null", response.getBody());
        verify(linkedWalletService, never()).linkWallet(any(), any(), any());
    }

    @Test
    void testLinkWallet_ExceptionThrown() {
        // Arrange
        String token = "Bearer valid.jwt.token";
        String jwtToken = "valid.jwt.token";
        LinkWalletRequest request = new LinkWalletRequest();
        request.setNetwork("Ethereum");
        request.setWalletAddress("0x123abc");

        when(jwtUtil.extractUserIdFromToken(jwtToken)).thenThrow(new RuntimeException("Token failure"));

        // Act
        ResponseEntity<?> response = controller.linkWallet(request, token);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("An error occurred: Token failure"));
    }
}
