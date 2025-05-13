package com.example.swapSafe.dto;

import java.time.LocalDateTime;

public class LinkedWalletResponseDTO {
    private String network;
    private String walletAddress;
    private LocalDateTime linkedAt;

    public LinkedWalletResponseDTO(String network, String walletAddress, LocalDateTime linkedAt) {
        this.network = network;
        this.walletAddress = walletAddress;
        this.linkedAt = linkedAt;
    }

    // Getters
    public String getNetwork() {
        return network;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public LocalDateTime getLinkedAt() {
        return linkedAt;
    }
}
