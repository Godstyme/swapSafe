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
    private String asset; // e.g., TRX, USDT
    private String fromAddress;
    private String toAddress;

    @Getter
    private BigDecimal amount;

    private LocalDateTime depositedAt;

    @ManyToOne
    private User user;

    protected DepositTransaction() {}

    public DepositTransaction(User user, String network, String asset, String fromAddress, String toAddress, BigDecimal amount) {
        this.user = user;
        this.network = network;
        this.asset = asset;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
        this.depositedAt = LocalDateTime.now();
    }
}
