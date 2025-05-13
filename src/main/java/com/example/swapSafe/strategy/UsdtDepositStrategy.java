package com.example.swapSafe.strategy;

import com.example.swapSafe.model.DepositTransaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("USDT")
public class UsdtDepositStrategy implements DepositStrategy {

    @Override
    public void processDeposit(DepositTransaction deposit) {
        System.out.println("Processing USDT deposit for wallet: " + deposit.getWalletAddress());
    }

    @Override
    public void validate(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.TEN) < 0) {
            throw new IllegalArgumentException("Minimum USDT deposit is 10");
        }
    }
} 
