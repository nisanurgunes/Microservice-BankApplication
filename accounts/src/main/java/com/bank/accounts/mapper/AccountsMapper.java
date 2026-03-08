package com.bank.accounts.mapper;

import com.bank.accounts.dto.AccountsDto;
import com.bank.accounts.entity.Accounts;

public class AccountsMapper {
  public static AccountsDto mapToAccountsDto(Accounts accounts) {
    return new AccountsDto(
        accounts.getAccountNumber(), accounts.getAccountType().name(), accounts.getBranchAddress());
  }

  public static Accounts mapToAccountsEntity(AccountsDto accountsDto) {
    Accounts accounts = new Accounts();
    accounts.setAccountNumber(accountsDto.accountNumber());
    accounts.setAccountType(Accounts.AccountType.valueOf(accountsDto.accountType().toUpperCase()));
    accounts.setBranchAddress(accountsDto.branchAddress());
    return accounts;
  }
}
