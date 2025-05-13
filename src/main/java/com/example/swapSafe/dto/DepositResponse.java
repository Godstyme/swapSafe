package com.example.swapSafe.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DepositResponse {
    private String walletAddress;
    private BigDecimal amount;
    private String network;
    private LocalDateTime depositedAt;

    public DepositResponse(String walletAddress, BigDecimal amount, String network, LocalDateTime depositedAt) {
        this.walletAddress = walletAddress;
        this.amount = amount;
        this.network = network;
        this.depositedAt = depositedAt;
    }

    public String getWalletAddress() { return walletAddress; }
    public BigDecimal getAmount() { return amount; }
    public String getNetwork() { return network; }
    public LocalDateTime getDepositedAt() { return depositedAt; }
}