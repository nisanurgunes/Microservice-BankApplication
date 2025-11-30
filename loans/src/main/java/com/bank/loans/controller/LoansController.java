package com.bank.loans.controller;

import com.bank.loans.constants.LoansConstants;
import com.bank.loans.dto.LoansContactInfoDto;
import com.bank.loans.dto.LoansDto;
import com.bank.loans.dto.ResponseDto;
import com.bank.loans.service.LoansService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "CRUD REST APIs for Loans in Microservice",
    description = "CRUD REST APIs in Microservice to CREATE, UPDATE, FETCH AND DELETE loan details")
@RestController
@RequestMapping(
    path = "/loans",
    produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class LoansController {

  private final LoansService loansService;
  private final LoansContactInfoDto loansContactInfoDto;
  private final Environment environment;

  public LoansController(
      LoansService loansService, LoansContactInfoDto loansContactInfoDto, Environment environment) {
    this.loansService = loansService;
    this.loansContactInfoDto = loansContactInfoDto;
    this.environment = environment;
  }

  @Value("${build.version:1.0.0-default}")
  private String buildVersion;

  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createLoan(
      @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    loansService.createNewLoan(mobileNumber);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
  }

  @GetMapping("/get-info")
  public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam String loanNumber) {
    LoansDto loansDto = loansService.fetchLoan(loanNumber);
    return ResponseEntity.status(HttpStatus.OK).body(loansDto);
  }

  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
    boolean isUpdated = loansService.updateLoan(loansDto);
    if (isUpdated) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteLoanDetails(
      @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    boolean isDeleted = loansService.deleteLoan(mobileNumber);
    if (isDeleted) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
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
  public ResponseEntity<LoansContactInfoDto> getContactInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(loansContactInfoDto);
  }
}
