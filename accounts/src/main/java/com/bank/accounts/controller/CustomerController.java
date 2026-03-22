package com.bank.accounts.controller;

import com.bank.accounts.dto.CustomerDetailsDto;
import com.bank.accounts.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
    name = "REST APIs for Customer details in EazyBank",
    description = "REST APIs in Microservice to fetch aggregated customer details")
@RequestMapping(
    path = "/accounts",
    produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {
  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Operation(summary = "Fetch customer, account, card and loan details by account id")
  @GetMapping("/customer-details")
  public ResponseEntity<CustomerDetailsDto> getCustomerDetails(
      @RequestParam("accountsId") @NotBlank(message = "accountsId must not be blank")
          String accountsId) {
    return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerDetails(accountsId));
  }
}
