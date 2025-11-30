package com.bank.accounts.repository;

import com.bank.accounts.entity.Accounts;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, String> {
  Optional<Accounts> findByAccountsId(String accountsId);

  Optional<Accounts> findByCustomerId(String customerId);

  @Transactional
  @Modifying
  void deleteByCustomerId(String customerId);
}
