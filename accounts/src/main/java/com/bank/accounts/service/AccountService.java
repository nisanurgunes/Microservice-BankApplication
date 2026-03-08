package com.bank.accounts.service;

import com.bank.accounts.dto.AccountsDto;
import com.bank.accounts.dto.CustomerDto;
import com.bank.accounts.entity.Accounts;
import com.bank.accounts.entity.Customer;
import com.bank.accounts.exception.CustomerAlreadyExistException;
import com.bank.accounts.exception.ResourceNotFoundException;
import com.bank.accounts.mapper.AccountsMapper;
import com.bank.accounts.mapper.CustomerMapper;
import com.bank.accounts.repository.AccountsRepository;
import com.bank.accounts.repository.CustomerRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  private final AccountsRepository accountsRepository;
  private final CustomerRepository customerRepository;

  public AccountService(
      AccountsRepository accountsRepository,
      CustomerRepository customerRepository,
      @Value("${address.defaultBranchAddress}") String defaultBranchAddress) {
    this.accountsRepository = accountsRepository;
    this.customerRepository = customerRepository;
    this.defaultBranchAddress = defaultBranchAddress;
  }

  private final String defaultBranchAddress;

  public void createAccount(CustomerDto customerDto) {

    Customer customer = CustomerMapper.mapToCustomerEntity(customerDto);

    if (customerRepository
        .findByCustomerMobileNumber(customerDto.customerMobileNumber())
        .isPresent()) {
      throw new CustomerAlreadyExistException("Customer exists");
    }

    Customer savedCustomer = customerRepository.save(customer);

    Accounts account = new Accounts();
    account.setCustomerId(savedCustomer.getCustomerId());
    account.setAccountNumber(generateAccountNumber());
    account.setAccountType(Accounts.AccountType.CHECKING);
    account.setBranchAddress(defaultBranchAddress);

    accountsRepository.save(account);
  }

  private String generateAccountNumber() {
    return UUID.randomUUID()
        .toString()
        .replaceAll("[^0-9]", "") // sadece rakamları al
        .substring(0, 10); // ilk 10 haneyi kullan
  }

  public AccountsDto getAccountInformation(String accountsId) {
    Accounts accounts =
        accountsRepository
            .findByAccountsId(accountsId)
            .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", accountsId));
    return AccountsMapper.mapToAccountsDto(accounts);
  }

  public boolean updateAccountsStatus(String accountsId, Accounts.AccountType accountType) {
    Accounts accounts =
        accountsRepository
            .findByAccountsId(accountsId)
            .orElseThrow(() -> new ResourceNotFoundException("Account", "accountsId", accountsId));
    accounts.setAccountType(accountType);
    accountsRepository.save(accounts);
    return true;
  }

  public boolean updateCustomerDetails(String customerId, CustomerDto customerDto) {
    boolean isUpdated = false;
    Customer customer =
        customerRepository
            .findByCustomerId(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId));
    Accounts account =
        accountsRepository
            .findByCustomerId(customer.getCustomerId())
            .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customerId));
    if (customerDto != null && account.getAccountType() != Accounts.AccountType.INACTIVE) {
      customer.setCustomerName(customerDto.customerName());
      customer.setCustomerEmail(customerDto.customerEmail());
      customer.setCustomerMobileNumber(customerDto.customerMobileNumber());
      customerRepository.save(customer);
      isUpdated = true;
    }
    return isUpdated;
  }

  public boolean deleteAccount(String accountsId) {
    Accounts account =
        accountsRepository
            .findByAccountsId(accountsId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", accountsId));
    accountsRepository.deleteByCustomerId(account.getCustomerId());
    if (account.getCustomerId() != null) {
      Customer customer =
          customerRepository
              .findByCustomerId(account.getCustomerId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Customer", "customerId", account.getCustomerId()));
      customerRepository.delete(customer);
    }
    return true;
  }
}
