package com.example.swapSafe.validator;


import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PiAddressValidator implements WalletAddressValidatorStrategy {

    private static final Pattern PI_PATTERN = Pattern.compile("^P[a-zA-Z0-9]{33}$");

    @Override
    public boolean isValid(String address) {
        return PI_PATTERN.matcher(address).matches();
    }

    @Override
    public String getSupportedNetwork() {
        return "PI";
    }
}