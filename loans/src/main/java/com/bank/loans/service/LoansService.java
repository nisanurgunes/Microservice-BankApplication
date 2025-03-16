package com.bank.loans.service;

import com.bank.loans.constants.LoansConstants;
import com.bank.loans.dto.LoansDto;
import com.bank.loans.entity.Loans;
import com.bank.loans.exception.LoanAlreadyExistsException;
import com.bank.loans.exception.ResourceNotFoundException;
import com.bank.loans.mapper.LoansMapper;
import com.bank.loans.repository.LoansRepository;
import java.util.Optional;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoansService {

  private LoansRepository loansRepository;

  public void createLoan(String mobileNumber) {
    Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
    if (optionalLoans.isPresent()) {
      throw new LoanAlreadyExistsException(
          "Loan already registered with given mobileNumber " + mobileNumber);
    }
    loansRepository.save(createNewLoan(mobileNumber));
  }

  private Loans createNewLoan(String mobileNumber) {
    Loans newLoan = new Loans();
    long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
    newLoan.setLoanNumber(Long.toString(randomLoanNumber));
    newLoan.setMobileNumber(mobileNumber);
    newLoan.setLoanType(LoansConstants.HOME_LOAN);
    newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
    newLoan.setAmountPaid(0);
    newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
    return newLoan;
  }

  public LoansDto fetchLoan(String mobileNumber) {
    Loans loans =
        loansRepository
            .findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
    return LoansMapper.mapToLoansDto(loans, new LoansDto());
  }

  public boolean updateLoan(LoansDto loansDto) {
    Loans loans =
        loansRepository
            .findByLoanNumber(loansDto.getLoanNumber())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
    LoansMapper.mapToLoans(loansDto, loans);
    loansRepository.save(loans);
    return true;
  }

  public boolean deleteLoan(String mobileNumber) {
    Loans loans =
        loansRepository
            .findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
    loansRepository.deleteById(loans.getLoanId());
    return true;
  }
}
