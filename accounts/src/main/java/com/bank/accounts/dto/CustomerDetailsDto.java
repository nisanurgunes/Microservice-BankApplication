package com.bank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
    name = "CustomerDetailsDto",
    description = "Schema to hold Customer, Accounts and Loans details")
public class CustomerDetailsDto {
  @NotBlank(message = "Name should not be empty or null")
  @Size(min = 5, max = 30, message = "The lenght of the customer name should be between 5 and 30")
  String customerName;

  @NotBlank(message = "Email should not be empty or null")
  @Email(message = "Email adress should be valid value")
  String customerEmail;

  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digits")
  String customerMobileNumber;

  @Schema(description = "Accounts details of the customer")
  private AccountsDto accountsDto;

  @Schema(description = "Cards details of the customer")
  private CardsDto cardsDto;

  @Schema(description = "Loans details of the customer")
  private LoansDto loansDto;
}
