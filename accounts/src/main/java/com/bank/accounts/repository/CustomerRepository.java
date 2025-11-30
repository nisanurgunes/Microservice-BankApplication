package com.bank.accounts.repository;

import com.bank.accounts.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

  Optional<Customer> findByCustomerMobileNumber(String customerMobileNumber);

  Optional<Customer> findByCustomerId(String customerId);
}
