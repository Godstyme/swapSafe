package com.example.swapSafe.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetResponse {
    private String network;
    private double balance;
    private double price;

    public AssetResponse(String network, double balance, double price) {
        this.network = network;
        this.balance = balance;
        this.price = price;
    }
}
