package com.example.swapSafe.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Getter
@Setter
public class DepositRequest {

    @NotBlank
    private String network;

    @NotBlank
    private String asset;

    @NotBlank
    private String fromAddress;

    @NotBlank
    private String toAddress;

    @NotNull
    private BigDecimal amount;
}
