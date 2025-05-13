package com.example.swapSafe.controller;

import com.example.swapSafe.dto.AssetResponse;
import com.example.swapSafe.dto.DepositRequest;
import com.example.swapSafe.dto.DepositResponse;
import com.example.swapSafe.service.DepositService;
import com.example.swapSafe.model.DepositTransaction;
import com.example.swapSafe.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/deposit")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class DepositController {

    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/simulate")
    public ResponseEntity<String> deposit(@RequestBody DepositRequest request) {
        try {
            depositService.processDeposit(request);
            return ResponseEntity.ok("Deposit successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/users_wallet/{walletAddress}")
    public User getUserByWalletAddress(@PathVariable String walletAddress) {
        return depositService.getUserByWalletAddress(walletAddress);
    }

    @GetMapping("/transactions/{walletAddress}")
    public List<DepositTransaction> getDepositsByWalletAddress(@PathVariable String walletAddress) {
        return depositService.getDepositsByWalletAddress(walletAddress);
    }

    @GetMapping("/assets/{walletAddress}")
    public Map<String, List<AssetResponse>> getAssetsByWalletAddress(@PathVariable String walletAddress) {
        List<AssetResponse> assets = depositService.getAssetsByWalletAddress(walletAddress);
        return Map.of("assets", assets);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserDeposits(@PathVariable Long id) {
        try {
            List<DepositTransaction> deposits = depositService.getUserDeposits(id);
            return ResponseEntity.ok(deposits);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching deposits: " + e.getMessage());
        }
    }
    
}
