package com.example.swapSafe.controller;


import com.example.swapSafe.dto.LinkWalletRequest;
import com.example.swapSafe.security.JwtUtil;
import com.example.swapSafe.service.LinkedWalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/wallet")
public class WalletLinkController {

    private final LinkedWalletService linkedWalletService;
    private final JwtUtil jwtUtil;

    public WalletLinkController(LinkedWalletService linkedWalletService,  JwtUtil jwtUtil) {
        this.linkedWalletService = linkedWalletService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/link")
    public ResponseEntity<?> linkWallet(@RequestBody LinkWalletRequest request,
                                        @RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is missing or invalid");
        }

        if (request == null || request.getNetwork() == null || request.getWalletAddress() == null) {
            return ResponseEntity.badRequest().body("Network and wallet address must not be null");
        }

        try {
            String jwtToken = token.substring(7);
            Long userId = jwtUtil.extractUserIdFromToken(jwtToken);
            linkedWalletService.linkWallet(request.getNetwork(), request.getWalletAddress(), userId);
            return ResponseEntity.ok("Wallet linked successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

}
