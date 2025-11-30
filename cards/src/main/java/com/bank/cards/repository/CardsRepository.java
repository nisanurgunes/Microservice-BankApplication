package com.bank.cards.repository;

import com.bank.cards.entity.Cards;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardsRepository extends JpaRepository<Cards, String> {

  Optional<Cards> findByCustomerMobileNumber(String customerMobileNumber);

  Optional<Cards> findByCardNumber(String cardNumber);
}
