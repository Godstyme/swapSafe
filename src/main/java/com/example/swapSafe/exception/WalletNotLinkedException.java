package com.example.swapSafe.exception;

public class WalletNotLinkedException extends RuntimeException {
    public WalletNotLinkedException(String message) {
        super(message);
    }
}