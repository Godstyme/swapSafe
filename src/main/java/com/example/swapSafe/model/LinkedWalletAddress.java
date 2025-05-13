package com.example.swapSafe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class LinkedWalletAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String network;

    private String walletAddress;

    @Column(name = "linked_at")
    private LocalDateTime linkedAt;

    protected LinkedWalletAddress() {

    }


    public LinkedWalletAddress(User user, String network, String walletAddress) {
        this.user = user;
        this.network = network;
        this.walletAddress = walletAddress;
        this.linkedAt = LocalDateTime.now();
    }

}
