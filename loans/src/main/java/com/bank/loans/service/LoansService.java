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

  public void createNewLoan(String mobileNumber) {

    Loans newLoan = new Loans();

    String loanNumber = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    newLoan.setLoanNumber(loanNumber);

    newLoan.setCustomerMobileNumber(mobileNumber);
    newLoan.setLoanType(Loans.LoanType.PERSONAL);

    newLoan.setTotalLoan(new BigDecimal("100000.00"));
    newLoan.setAmountPaid(BigDecimal.ZERO);
    newLoan.setOutstandingAmount(newLoan.getTotalLoan());

    newLoan.setCreatedBy("SYSTEM");
    newLoan.setCreatedAt(LocalDateTime.now());

    loansRepository.save(newLoan);
  }

  public LoansDto fetchLoan(String loanNumber) {
    Loans loans =
        loansRepository
            .findByLoanNumber(loanNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Loan", "loanNumber", loanNumber));
    return LoansMapper.mapToLoansDto(loans);
  }

  public boolean updateLoan(LoansDto loansDto) {
    Loans loans =
        loansRepository
            .findByLoanNumber(loansDto.loanNumber())
            .orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.loanNumber()));
    loans.setCustomerMobileNumber(loansDto.customerMobileNumber());
    loans.setLoanType(Loans.LoanType.valueOf(loansDto.loanType().toUpperCase()));
    loans.setTotalLoan(loansDto.totalLoan());
    loans.setAmountPaid(loansDto.amountPaid());
    loans.setOutstandingAmount(loansDto.outstandingAmount());
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
