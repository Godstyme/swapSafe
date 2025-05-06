package com.example.swapSafe.factory;


import com.example.swapSafe.validator.WalletAddressValidatorStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

//@Component
//public class WalletAddressValidatorFactory {
//    private final Map<String, WalletAddressValidatorStrategy> validatorMap;
//
//    public WalletAddressValidatorFactory(List<WalletAddressValidatorStrategy> validators) {
//        this.validatorMap = validators.stream()
//                .collect(Collectors.toMap(
//                        v -> v.getSupportedNetwork().toUpperCase(),
//                        Function.identity()
//                ));
//    }
//
//    public WalletAddressValidatorStrategy getValidator(String network) {
//        if (network == null) {
//            throw new IllegalArgumentException("Network cannot be null");
//        }
//
//        return Optional.ofNullable(validatorMap.get(network.toUpperCase()))
//                .orElseThrow(() -> new IllegalArgumentException("Unsupported network: " + network));
//    }
//
//}

@Component
public class WalletAddressValidatorFactory {

    private final Map<String, WalletAddressValidatorStrategy> validators;

    @Autowired
    public WalletAddressValidatorFactory(List<WalletAddressValidatorStrategy> strategies) {
        this.validators = strategies.stream()
                .collect(Collectors.toMap(WalletAddressValidatorStrategy::getSupportedNetwork, Function.identity()));
    }

    public WalletAddressValidatorStrategy getValidator(String network) {
        WalletAddressValidatorStrategy validator = validators.get(network.toUpperCase());
        if (validator == null) {
            throw new IllegalArgumentException("Unsupported network: " + network);
        }
        return validator;
    }
}

