package com.example.swapSafe.factory;

import com.example.swapSafe.strategy.DepositStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DepositStrategyFactory {
    private final Map<String, DepositStrategy> strategies;

    @Autowired
    public DepositStrategyFactory(Map<String, DepositStrategy> strategies) {
        this.strategies = strategies;
    }
    public DepositStrategy getStrategy(String network) {
        if (network == null || network.trim().isEmpty()) {
            throw new IllegalArgumentException("Network must not be null or empty.");
        }

        DepositStrategy strategy = strategies.get(network.toUpperCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported network: " + network);
        }
        return strategy;
    }

}
