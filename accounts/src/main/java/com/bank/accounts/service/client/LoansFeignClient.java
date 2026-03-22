package com.bank.accounts.service.client;

import com.bank.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoansFeignClient {
  @GetMapping("/loans/get-info-by-mobile")
  public ResponseEntity<LoansDto> fetchLoanDetails(
      @RequestParam("mobileNumber") String mobileNumber);
}
