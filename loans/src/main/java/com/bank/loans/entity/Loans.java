package com.bank.loans.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Loans extends BaseEntity {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "loans_id")
  private String loanId;

  @Column(name = "customer_mobile_number")
  private String customerMobileNumber;

  @Column(name = "loan_number")
  private String loanNumber;

  @Column(name = "loan_type")
  @Enumerated(EnumType.STRING)
  private LoanType loanType = LoanType.PERSONAL;

  @Column(name = "total_loan")
  private BigDecimal totalLoan;

  @Column(name = "amount_paid")
  private BigDecimal amountPaid;

  @Column(name = "outstanding_amount")
  private BigDecimal outstandingAmount;

  public enum LoanType {
    PERSONAL, // Bireysel ihtiyaç kredisi
    MORTGAGE, // Konut kredisi
    AUTO, // Araç kredisi
    STUDENT, // Öğrenci / eğitim kredisi
    BUSINESS, // Ev ipoteğine bağlı ek kredi
    PAYDAY, // Tarım kredisi
    CREDIT_CARD // Borç birleştirme kredisi
  }

  public String getLoanId() {
    return loanId;
  }

  public void setLoanId(String loanId) {
    this.loanId = loanId;
  }

  public String getCustomerMobileNumber() {
    return customerMobileNumber;
  }

  public void setCustomerMobileNumber(String customerMobileNumber) {
    this.customerMobileNumber = customerMobileNumber;
  }

  public String getLoanNumber() {
    return loanNumber;
  }

  public void setLoanNumber(String loanNumber) {
    this.loanNumber = loanNumber;
  }

  public LoanType getLoanType() {
    return loanType;
  }

  public void setLoanType(LoanType loanType) {
    this.loanType = loanType;
  }

  public BigDecimal getTotalLoan() {
    return totalLoan;
  }

  public void setTotalLoan(BigDecimal totalLoan) {
    this.totalLoan = totalLoan;
  }

  public BigDecimal getAmountPaid() {
    return amountPaid;
  }

  public void setAmountPaid(BigDecimal amountPaid) {
    this.amountPaid = amountPaid;
  }

  public BigDecimal getOutstandingAmount() {
    return outstandingAmount;
  }

  public void setOutstandingAmount(BigDecimal outstandingAmount) {
    this.outstandingAmount = outstandingAmount;
  }
}
