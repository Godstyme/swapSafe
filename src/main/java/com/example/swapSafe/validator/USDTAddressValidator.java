package com.example.swapSafe.validator;


import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class USDTAddressValidator implements WalletAddressValidatorStrategy {

    private static final Pattern USDT_PATTERN = Pattern.compile("^Ux[0-9a-fA-F]{40}$");

    @Override
    public boolean isValid(String address) {
        return USDT_PATTERN.matcher(address).matches();
    }

    @Override
    public String getSupportedNetwork() {
        return "USDT";
    }
}