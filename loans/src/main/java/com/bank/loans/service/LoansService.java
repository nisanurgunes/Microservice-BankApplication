package com.bank.loans.service;

import com.bank.loans.dto.LoansDto;
import com.bank.loans.entity.Loans;
import com.bank.loans.exception.ResourceNotFoundException;
import com.bank.loans.mapper.LoansMapper;
import com.bank.loans.repository.LoansRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoansService {

  private LoansRepository loansRepository;

  public Loans createNewLoan(String mobileNumber) {

    Loans newLoan = new Loans();

    String loanNumber =
        "LN-" + UUID.randomUUID().toString().substring(0, 13).replace("-", "").toUpperCase();
    newLoan.setLoanNumber(loanNumber);

    newLoan.setCustomerMobileNumber(mobileNumber);
    newLoan.setLoanType(Loans.LoanType.PERSONAL);

    newLoan.setTotalLoan(new BigDecimal("100000.00"));
    newLoan.setAmountPaid(BigDecimal.ZERO);
    newLoan.setOutstandingAmount(newLoan.getTotalLoan());

    newLoan.setCreatedBy("SYSTEM");
    newLoan.setCreatedAt(LocalDateTime.now());

    return newLoan;
  }

  public LoansDto fetchLoan(String loanNumber) {
    Loans loans =
        loansRepository
            .findByLoanNumber(loanNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", loanNumber));
    return LoansMapper.mapToLoansDto(loans);
  }

  public boolean updateLoan(LoansDto loansDto) {
    Loans loans =
        loansRepository
            .findByLoanNumber(loansDto.loanNumber())
            .orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.loanNumber()));
    LoansMapper.mapToLoansEntity(loansDto);
    loansRepository.save(loans);
    return true;
  }

  public boolean deleteLoan(String loanNumber) {
    Loans loans =
        loansRepository
            .findByLoanNumber(loanNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Loan", "loanNumber", loanNumber));
    loansRepository.deleteById(loans.getLoanId());
    return true;
  }
}
