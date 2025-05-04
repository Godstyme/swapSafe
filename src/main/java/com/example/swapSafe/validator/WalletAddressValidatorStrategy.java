package com.example.swapSafe.validator;

public interface WalletAddressValidatorStrategy {
    boolean isValid(String address);
    String getSupportedNetwork();
}
