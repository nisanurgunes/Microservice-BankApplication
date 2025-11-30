package com.bank.accounts.mapper;

import com.bank.accounts.dto.AccountsDto;
import com.bank.accounts.dto.CustomerDto;
import com.bank.accounts.entity.Accounts;
import com.bank.accounts.entity.Customer;

public class CustomerMapper {
  public static CustomerDto mapToCustomerDto(Customer customer, Accounts accounts) {
    AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(accounts);

    return new CustomerDto(
        customer.getName(), customer.getCustomerEmail(), customer.getCustomerMobileNumber());
  }

  public static Customer mapToCustomerEntity(CustomerDto dto) {
    Customer customer = new Customer();
    customer.setName(dto.customerName());
    customer.setCustomerEmail(dto.customerEmail());
    customer.setCustomerMobileNumber(dto.customerMobileNumber());
    return customer;
  }
}
