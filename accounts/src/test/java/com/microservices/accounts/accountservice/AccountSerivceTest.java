package com.microservices.accounts.accountservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.bank.accounts.constants.AccountsConstants;
import com.bank.accounts.dto.AccountsDto;
import com.bank.accounts.dto.CustomerDto;
import com.bank.accounts.entity.Accounts;
import com.bank.accounts.entity.Customer;
import com.bank.accounts.mapper.CustomerMapper;
import com.bank.accounts.repository.AccountsRepository;
import com.bank.accounts.repository.CustomerRepository;
import com.bank.accounts.service.AccountService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountSerivceTest {
  @Mock private AccountsRepository accountsRepository;
  @Mock private CustomerRepository customerRepository;
  @InjectMocks private AccountService accountService;
  private CustomerDto customerDto;
  private AccountsDto accountsDto;
  private Accounts accounts;
  private Customer customer;

  @BeforeEach
  public void setUp() {
    customerDto = new CustomerDto();
    customerDto.setName("Nisa");
    customerDto.setEmail("nisa@gmail.com");
    customerDto.setMobileNumber("12345678912");

    customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
    customer.setCustomerId(1L);
    accounts = new Accounts();
    accounts.setAccountNumber(12345678912L);
    accounts.setCustomerId(customer.getCustomerId());
    accounts.setAccountType(AccountsConstants.SAVINGS);
    accounts.setBranchAddress("123 Main Street, New York");
  }

  @Test
  void createAcount_Success() {
    when(customerRepository.findByMobileNumber(customerDto.getMobileNumber()))
        .thenReturn(Optional.empty());
    when(customerRepository.save(any(Customer.class))).thenReturn(customer);

    accountService.createAccount(customerDto);

    verify(customerRepository, times(1)).findByMobileNumber(customerDto.getMobileNumber());
    verify(customerRepository, times(1)).save(any(Customer.class));
    verify(accountsRepository, times(1)).save(any(Accounts.class));
  }
}
