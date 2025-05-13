package com.example.swapSafe.strategy;
import com.example.swapSafe.model.DepositTransaction;

import java.math.BigDecimal;

public interface DepositStrategy {
    void processDeposit(DepositTransaction deposit);
    void validate(BigDecimal amount);
}
