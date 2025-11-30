package com.bank.accounts.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Accounts extends BaseEntity {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "accounts_id")
  private String accountsId;

  @Column(name = "customer_id")
  private String customerId;

  @Column(name = "account_number")
  private String accountNumber;

  @Column(name = "account_type")
  @Enumerated(EnumType.STRING)
  private AccountType accountType = AccountType.CHECKING;

  @Column(name = "branch_address")
  private String branchAddress;

  public enum AccountType {
    ACTIVE,
    INACTIVE,
    FIXED,
    CHECKING
  }

  public String getAccountsId() {
    return accountsId;
  }

  public void setAccountsId(String accountsId) {
    this.accountsId = accountsId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }

  public String getBranchAddress() {
    return branchAddress;
  }

  public void setBranchAddress(String branchAddress) {
    this.branchAddress = branchAddress;
  }
}
