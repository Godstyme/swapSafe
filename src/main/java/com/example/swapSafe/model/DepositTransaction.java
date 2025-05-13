package com.example.swapSafe.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;



@Entity
public class DepositTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String network;

    private String walletAddress;

    private BigDecimal amount;

    private LocalDateTime timestamp;

    @ManyToOne
    private User user;

    // Builder
    public static class Builder {
        private final DepositTransaction deposit = new DepositTransaction();

        public Builder network(String network) {
            deposit.network = network;
            return this;
        }

        public Builder walletAddress(String walletAddress) {
            deposit.walletAddress = walletAddress;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            deposit.amount = amount;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            deposit.timestamp = timestamp;
            return this;
        }

        public Builder user(User user) {
            deposit.user = user;
            return this;
        }

        public DepositTransaction build() {
            return deposit;
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getNetwork() { return network; }
    public String getWalletAddress() { return walletAddress; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public User getUser() { return user; }
}

