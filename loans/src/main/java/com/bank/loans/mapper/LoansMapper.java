package com.bank.loans.mapper;

import com.bank.loans.dto.LoansDto;
import com.bank.loans.entity.Loans;

public class LoansMapper {

  public static LoansDto mapToLoansDto(Loans loans) {
    return new LoansDto(
        loans.getCustomerMobileNumber(),
        loans.getLoanNumber(),
        loans.getLoanType().name(),
        loans.getTotalLoan(),
        loans.getAmountPaid(),
        loans.getOutstandingAmount());
  }

  public static Loans mapToLoansEntity(LoansDto loansDto) {
    Loans loans = new Loans();
    loans.setLoanNumber(loansDto.loanNumber());
    loans.setLoanType(Loans.LoanType.valueOf(loansDto.loanType().toUpperCase()));
    loans.setCustomerMobileNumber(loansDto.customerMobileNumber());
    loans.setTotalLoan(loansDto.totalLoan());
    loans.setAmountPaid(loansDto.amountPaid());
    loans.setOutstandingAmount(loansDto.outstandingAmount());
    return loans;
  }
}
