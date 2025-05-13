package com.example.swapSafe.strategy;

import com.example.swapSafe.model.DepositTransaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("PI")
public class PIDepositStrategy implements DepositStrategy {

    @Override
    public void processDeposit(DepositTransaction deposit) {
        System.out.println("Processing PI deposit for wallet: " + deposit.getWalletAddress());
        // You can do further steps here (e.g., logging or notifications)
    }

    @Override
    public void validate(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.TEN) < 0) {
            throw new IllegalArgumentException("Minimum PI deposit is 10");
        }
    }
}
