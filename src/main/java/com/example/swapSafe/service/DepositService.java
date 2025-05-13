package com.example.swapSafe.service;


import com.example.swapSafe.dto.AssetResponse;
import com.example.swapSafe.dto.DepositRequest;
import com.example.swapSafe.factory.DepositStrategyFactory;
import com.example.swapSafe.model.User;
import com.example.swapSafe.model.UserAssetBalance;
import com.example.swapSafe.repository.DepositTransactionRepository;
import com.example.swapSafe.repository.UserAssetBalanceRepository;
import com.example.swapSafe.repository.UserRepository;
import com.example.swapSafe.strategy.DepositStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.swapSafe.model.DepositTransaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepositService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepositTransactionRepository depositRepository;

    @Autowired
    private DepositStrategyFactory strategyFactory;

    @Autowired
    private UserAssetBalanceRepository assetBalanceRepository;

    public User getUserByWalletAddress(String walletAddress) {
        return userRepository.findByLinkedWalletAddresses_WalletAddress(walletAddress)
                .orElseThrow(() -> new IllegalArgumentException("User with wallet address " + walletAddress + " not found"));
    }

    public void processDeposit(DepositRequest request) {
        User user = getUserByWalletAddress(request.getWalletAddress());

        DepositStrategy strategy = strategyFactory.getStrategy(request.getNetwork());
        strategy.validate(request.getAmount());

        DepositTransaction deposit = new DepositTransaction.Builder()
                .network(request.getNetwork())
                .walletAddress(request.getWalletAddress())
                .amount(request.getAmount())
                .timestamp(LocalDateTime.now())
                .user(user)
                .build();

        depositRepository.save(deposit);

        // 🔄 Track balance per asset
        UserAssetBalance assetBalance = assetBalanceRepository.findByUserAndNetwork(user, request.getNetwork())
                .orElseGet(() -> {
                    UserAssetBalance newBalance = new UserAssetBalance();
                    newBalance.setUser(user);
                    newBalance.setNetwork(request.getNetwork());
                    newBalance.setBalance(BigDecimal.ZERO);
                    return newBalance;
                });

        assetBalance.setBalance(assetBalance.getBalance().add(request.getAmount()));
        assetBalance.setLastUpdated(LocalDateTime.now());
        assetBalanceRepository.save(assetBalance);
    }


    public List<DepositTransaction> getDepositsByWalletAddress(String walletAddress) {
        return depositRepository.findByWalletAddress(walletAddress);
    }

    public List<DepositTransaction> getDepositsByUser(Long userId) {

        return depositRepository.findByUserId(userId);
    }

    public BigDecimal getBalanceByNetwork(User user, String network) {
        return assetBalanceRepository.findByUserAndNetwork(user, network)
                .map(UserAssetBalance::getBalance)
                .orElse(BigDecimal.ZERO);
    }
    public List<AssetResponse> getAssetsByWalletAddress(String walletAddress) {
        List<DepositTransaction> transactions = getDepositsByWalletAddress(walletAddress);

        Map<String, Double> prices = Map.of(
                "PI", 1.0,
                "USDT", 1.0,
                "BTC", 62000.0,
                "ETH", 3100.0
        );

        // Aggregate balances by network
        Map<String, BigDecimal> balanceMap = new HashMap<>();

        for (DepositTransaction tx : transactions) {
            String network = tx.getNetwork();
            BigDecimal current = balanceMap.getOrDefault(network, BigDecimal.ZERO);
            balanceMap.put(network, current.add(tx.getAmount()));
        }

        
        return balanceMap.entrySet().stream()
                .map(entry -> new AssetResponse(
                        entry.getKey(),
                        entry.getValue().doubleValue(),
                        prices.getOrDefault(entry.getKey(), 0.0)
                ))
                .collect(Collectors.toList());
    }
    public List<DepositTransaction> getUserDeposits(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return depositRepository.findByUserId(userId);
    }


}

