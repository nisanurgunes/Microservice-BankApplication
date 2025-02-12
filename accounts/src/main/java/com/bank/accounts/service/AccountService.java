package com.bank.accounts.service;

import com.bank.accounts.dto.CustomerDto;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
  void createAccount(CustomerDto customerDto);

  CustomerDto fetchAccount(String mobileNumber);

  boolean updateAccount(CustomerDto customerDto);

  boolean deleteAccount(String mobileNumber);
}
