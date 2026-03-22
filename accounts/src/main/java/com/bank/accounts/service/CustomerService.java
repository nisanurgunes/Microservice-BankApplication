package com.bank.accounts.service;

import com.bank.accounts.dto.CardsDto;
import com.bank.accounts.dto.CustomerDetailsDto;
import com.bank.accounts.dto.LoansDto;
import com.bank.accounts.entity.Accounts;
import com.bank.accounts.entity.Customer;
import com.bank.accounts.exception.ResourceNotFoundException;
import com.bank.accounts.mapper.AccountsMapper;
import com.bank.accounts.mapper.CustomerMapper;
import com.bank.accounts.repository.AccountsRepository;
import com.bank.accounts.repository.CustomerRepository;
import com.bank.accounts.service.client.CardsFeignClient;
import com.bank.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    public CustomerDetailsDto getCustomerDetails(String accountsId) {
        Accounts accounts = accountsRepository.findByAccountsId(accountsId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountsId", accountsId));
        Customer customer = customerRepository.findByCustomerId(accounts.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", accounts.getCustomerId()));
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer,new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts));
        CardsDto cardsDto = cardsFeignClient.fetchCardDetails(customer.getCustomerMobileNumber()).getBody();
        LoansDto loansDto = loansFeignClient.fetchLoanDetails(customer.getCustomerMobileNumber()).getBody();
        customerDetailsDto.setCardsDto(cardsDto);
        customerDetailsDto.setLoansDto(loansDto);
        return customerDetailsDto;
    }
}
