package com.example.swapSafe.service;


import com.example.swapSafe.factory.WalletAddressValidatorFactory;
import com.example.swapSafe.model.LinkedWalletAddress;
import com.example.swapSafe.model.User;
import com.example.swapSafe.repository.LinkedWalletRepository;
import com.example.swapSafe.repository.UserRepository;
import com.example.swapSafe.validator.WalletAddressValidatorStrategy;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class LinkedWalletService {

    private final WalletAddressValidatorFactory validatorFactory;
    private final LinkedWalletRepository linkedWalletRepository;
    private  final UserRepository userRepository;

    public LinkedWalletService(WalletAddressValidatorFactory validatorFactory,LinkedWalletRepository linkedWalletRepository,UserRepository userRepository) {
        this.validatorFactory = validatorFactory;
        this.linkedWalletRepository = linkedWalletRepository;
        this.userRepository = userRepository;
    }

    public void linkWallet(String network, String walletAddress, Long userId) {
        WalletAddressValidatorStrategy validator = validatorFactory.getValidator(network);
        if (!validator.isValid(walletAddress)) {
            throw new IllegalArgumentException("Invalid wallet address '" + walletAddress + "' for network: " + network);
        }
//
        Optional<LinkedWalletAddress> existing = linkedWalletRepository.findByUserIdAndNetwork(userId, network);

        if (existing.isPresent()) {
            throw new IllegalArgumentException("User has already linked a wallet address to the " + network + " network.");
        }


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LinkedWalletAddress linkedWalletAddress = new LinkedWalletAddress(user, network, walletAddress);
         linkedWalletRepository.save(linkedWalletAddress);

        System.out.println("Wallet address linked successfully");
        System.out.println("Linked At: " + linkedWalletAddress.getLinkedAt());
    }

    public LinkedWalletAddress findLinkedWallet(String walletAddress, String network) {
        return linkedWalletRepository.findByWalletAddressAndNetwork(walletAddress, network)
                .orElseThrow(() -> new RuntimeException("Wallet not linked or does not exist."));
    }

}
