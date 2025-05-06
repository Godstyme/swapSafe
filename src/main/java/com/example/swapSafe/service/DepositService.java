package com.example.swapSafe.service;


import com.example.swapSafe.model.User;
import com.example.swapSafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DepositService {

    private final UserRepository userRepository;

    @Autowired
    public DepositService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public void depositFunds(Long userId, BigDecimal depositAmount) {
//        // Fetch the user from the repository
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Update the user's balance
//        BigDecimal updatedBalance = user.getBalance().add(depositAmount);
//
//        User updatedUser = User.builder()
//                .name(user.getName())
//                .email(user.getEmail())
//                .password(user.getPassword()) // Assuming password is not changing
//                .roles(user.getRoles())
//                .balance(updatedBalance)
//                .build();
//
//        // Save the updated user
//        userRepository.save(updatedUser);
//    }


    public void depositFunds(Long userId, BigDecimal depositAmount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setBalance(user.getBalance().add(depositAmount));
        userRepository.save(user);
    }

}
