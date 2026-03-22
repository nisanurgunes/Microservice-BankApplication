package com.bank.loans.repository;

import com.bank.loans.entity.Loans;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoansRepository extends JpaRepository<Loans, String> {

  Optional<Loans> findByLoanNumber(String loanNumber);

  Optional<Loans> findByCustomerMobileNumber(String customerMobileNumber);
}
