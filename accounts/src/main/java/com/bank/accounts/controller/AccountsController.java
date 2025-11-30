package com.bank.accounts.controller;

import com.bank.accounts.constants.AccountsConstants;
import com.bank.accounts.dto.*;
import com.bank.accounts.entity.Accounts;
import com.bank.accounts.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "CRUD REST APIs for Accounts in EazyBank",
    description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE account details")
@RestController
@RequestMapping(
    path = "/accounts",
    produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {
  private final AccountService accountService;
  private final Environment environment;
  private final AccountsContactInfoDto accountsContactInfoDto;

  @Value("${build.version:1.0.0-default}")
  private String buildVersion;

  public AccountsController(
      AccountService accountService,
      Environment environment,
      AccountsContactInfoDto accountsContactInfoDto) {
    this.accountService = accountService;
    this.environment = environment;
    this.accountsContactInfoDto = accountsContactInfoDto;
  }

  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
    accountService.createAccount(customerDto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
  }

  @GetMapping("/get-info")
  public ResponseEntity<AccountsDto> getAccountInformation(@RequestParam String accountsId) {
    AccountsDto accountsDto = accountService.getAccountInformation(accountsId);
    return ResponseEntity.status(HttpStatus.OK).body(accountsDto);
  }

  @PutMapping("/update-status")
  public ResponseEntity<Boolean> updateAccountStatus(
      @RequestParam String accountsId, @RequestParam Accounts.AccountType accountType) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(accountService.updateAccountsStatus(accountsId, accountType));
  }

  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateAccountDetails(
      @RequestParam String customerId, @Valid @RequestBody CustomerDto customerDto) {
    boolean isUpdated = accountService.updateCustomerDetails(customerId, customerDto);
    if (isUpdated) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(
              new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam String accountsId) {
    boolean isDeleted = accountService.deleteAccount(accountsId);
    if (isDeleted) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(
              new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
    }
  }

  @GetMapping("/build-info")
  public ResponseEntity<String> getBuildInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
  }

  @GetMapping("/java-version")
  public ResponseEntity<String> getJavaVersion() {
    return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
  }

  @GetMapping("/contact-info")
  public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
  }
}
