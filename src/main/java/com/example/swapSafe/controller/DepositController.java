package com.example.swapSafe.controller;

import com.example.swapSafe.dto.DepositRequest;
import com.example.swapSafe.model.DepositTransaction;
import com.example.swapSafe.model.User;
import com.example.swapSafe.repository.DepositTransactionRepository;
import com.example.swapSafe.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/deposit")
public class DepositController {

    private final DepositTransactionRepository depositRepo;
    private final UserRepository userRepository;

    public DepositController(DepositTransactionRepository depositRepo, UserRepository userRepository) {
        this.depositRepo = depositRepo;
        this.userRepository = userRepository;
    }

    @PostMapping("/simulate")
    public ResponseEntity<String> simulateDeposit(@RequestBody DepositRequest request,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        DepositTransaction deposit = new DepositTransaction(
                user,
                request.getNetwork(),
                request.getAsset(),
                request.getFromAddress(),
                request.getToAddress(),
                request.getAmount()
        );

        depositRepo.save(deposit);
        return ResponseEntity.ok("Deposit simulated successfully.");
    }

    @GetMapping("/my-deposits")
    public List<DepositTransaction> getMyDeposits(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return depositRepo.findByUser(user);
    }

}
