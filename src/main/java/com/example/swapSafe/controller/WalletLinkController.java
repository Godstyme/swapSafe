package com.example.swapSafe.controller;


import com.example.swapSafe.dto.LinkWalletRequest;
import com.example.swapSafe.dto.LinkedWalletResponseDTO;
import com.example.swapSafe.model.LinkedWalletAddress;
import com.example.swapSafe.model.User;
import com.example.swapSafe.repository.UserRepository;
import com.example.swapSafe.security.JwtUtil;
import com.example.swapSafe.service.LinkedWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/wallet")
@CrossOrigin(origins = "http://localhost:5173/")
public class WalletLinkController {

    private final LinkedWalletService linkedWalletService;
    private final JwtUtil jwtUtil;

    @Autowired
    private  UserRepository repo;

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

  @GetMapping("/linked-wallet")
    public ResponseEntity<LinkedWalletResponseDTO> getLinkedWalletByNetwork(
            @RequestParam String network,
            @RequestHeader("Authorization") String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String jwtToken = token.substring(7);
        String email = jwtUtil.extractEmailFromToken(jwtToken);

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user.getLinkedWalletAddresses().stream()
                .filter(wallet -> wallet.getNetwork().equalsIgnoreCase(network))
                .findFirst()
                .map(wallet -> new LinkedWalletResponseDTO(
                        wallet.getNetwork(),
                        wallet.getWalletAddress(),
                        wallet.getLinkedAt()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }





}
