package com.example.swapSafe.controller;

import com.example.swapSafe.dto.DepositRequest;
import com.example.swapSafe.factory.WalletAddressValidatorFactory;
import com.example.swapSafe.model.DepositTransaction;
import com.example.swapSafe.model.User;
import com.example.swapSafe.repository.DepositTransactionRepository;
import com.example.swapSafe.repository.UserRepository;
import com.example.swapSafe.validator.WalletAddressValidatorStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/deposit")
public class DepositController {

    private final DepositTransactionRepository depositRepo;
    private final UserRepository userRepository;
    

    private final WalletAddressValidatorFactory validatorFactory;

    public DepositController(DepositTransactionRepository depositRepo, UserRepository userRepository,WalletAddressValidatorFactory validatorFactory) {
        this.depositRepo = depositRepo;
        this.userRepository = userRepository;
        this.validatorFactory = validatorFactory;
    }

    @PostMapping("/simulate")
    public ResponseEntity<String> deposit(
            @RequestBody DepositRequest depositRequest
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();

        String email = jwt.getClaimAsString("email");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // ✅ Wallet validation using strategy pattern
        WalletAddressValidatorStrategy validator = validatorFactory.getValidator(depositRequest.getNetwork());

        if (!validator.isValid(depositRequest.getFromAddress())) {
            return ResponseEntity.badRequest().body("Invalid fromAddress for network: " + depositRequest.getNetwork());
        }

        if (!validator.isValid(depositRequest.getToAddress())) {
            return ResponseEntity.badRequest().body("Invalid toAddress for network: " + depositRequest.getNetwork());
        }

        // 💰 Process deposit
        DepositTransaction deposit = new DepositTransaction(
                user,
                depositRequest.getNetwork(),
                depositRequest.getAsset(),
                depositRequest.getFromAddress(),
                depositRequest.getToAddress(),
                depositRequest.getAmount()
        );

        user.setBalance(user.getBalance().add(depositRequest.getAmount()));

        depositRepo.save(deposit);
        userRepository.save(user);

        return ResponseEntity.ok("Deposit successful for user: " + email);
    }

//    @PostMapping("/simulate")
//    public ResponseEntity<String> deposit(
//            @AuthenticationPrincipal Jwt jwt,
//            @RequestBody DepositRequest depositRequest
//    ) {
//        String email = jwt.getClaimAsString("email");
//
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        DepositTransaction deposit = new DepositTransaction(
//                user,
//                depositRequest.getNetwork(),
//                depositRequest.getAsset(),
//                depositRequest.getFromAddress(),
//                depositRequest.getToAddress(),
//                depositRequest.getAmount()
//        );
//        BigDecimal newBalance = user.getBalance().add(depositRequest.getAmount());
//        user.setBalance(newBalance);
//        depositRepo.save(deposit);
////        user.setBalance(user.getBalance().add(deposit.getAmount()));
//        userRepository.save(user);
//        return ResponseEntity.ok("Deposit successful for user: " + email);
//    }



//    @GetMapping("/my-deposits")
//    public List<DepositTransaction> getMyDeposits(@AuthenticationPrincipal UserDetails userDetails) {
//        User user = userRepository.findByEmail(userDetails.getUsername())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return depositRepo.findByUser(user);
//    }

}
