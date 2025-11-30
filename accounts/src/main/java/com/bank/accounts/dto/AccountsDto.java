package com.bank.accounts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AccountsDto(
    @NotBlank(message = "Account number should not be empty")
        @Pattern(regexp = "\\d{10}", message = "Account number must be exactly 10 digits")
        String accountNumber,
    @NotBlank(message = "Account type should not be empty") String accountType,
    @NotBlank(message = "Branch address should not be empty") String branchAddress) {}
