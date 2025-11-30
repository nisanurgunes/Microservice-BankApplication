package com.bank.accounts.dto;

import jakarta.validation.constraints.*;

public record CustomerDto(
    @NotBlank(message = "Name should not be empty or null")
        @Size(
            min = 5,
            max = 30,
            message = "The lenght of the customer name should be between 5 and 30")
        String customerName,
    @NotBlank(message = "Email should not be empty or null")
        @Email(message = "Email adress should be valid value")
        String customerEmail,
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digits")
        String customerMobileNumber) {}
