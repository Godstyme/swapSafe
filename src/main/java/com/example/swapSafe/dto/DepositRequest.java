package com.example.swapSafe.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Getter
@Setter

public class DepositRequest {
    private String walletAddress;
    private BigDecimal amount;
    private String network;

    public String getWalletAddress() { return walletAddress; }
    public void setWalletAddress(String walletAddress) { this.walletAddress = walletAddress; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getNetwork() { return network; }
    public void setNetwork(String network) { this.network = network; }
}
