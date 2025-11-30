package com.bank.cards.service;

import com.bank.cards.constants.CardsConstants;
import com.bank.cards.dto.CardsDto;
import com.bank.cards.entity.Cards;
import com.bank.cards.exception.CardAlreadyExistsException;
import com.bank.cards.exception.ResourceNotFoundException;
import com.bank.cards.mapper.CardsMapper;
import com.bank.cards.repository.CardsRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CardsService {

  private CardsRepository cardsRepository;

  public void createCard(String mobileNumber) {
    Optional<Cards> optionalCards = cardsRepository.findByCustomerMobileNumber(mobileNumber);
    if (optionalCards.isPresent()) {
      throw new CardAlreadyExistsException(
          "Card already registered with given mobileNumber " + mobileNumber);
    }
    cardsRepository.save(createNewCard(mobileNumber));
  }

  private Cards createNewCard(String mobileNumber) {
    Cards newCard = new Cards();

    long number = 1_0000_0000_0000_0000L + Math.abs(new Random().nextLong());
    newCard.setCardNumber(Long.toString(number).substring(0, 16));

    newCard.setCustomerMobileNumber(mobileNumber);
    newCard.setCardType(Cards.CardType.CREDIT);

    newCard.setTotalLimit(BigDecimal.valueOf(CardsConstants.NEW_CARD_LIMIT));
    newCard.setAmountUsed(new BigDecimal(0));
    newCard.setAvailableAmount(newCard.getTotalLimit());

    newCard.setCreatedBy("SYSTEM");
    newCard.setCreatedAt(LocalDateTime.now());

    return newCard;
  }

  public CardsDto fetchCard(String mobileNumber) {
    Cards cards =
        cardsRepository
            .findByCustomerMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
    return CardsMapper.mapToCardsDto(cards);
  }

  public boolean updateCard(CardsDto cardsDto) {
    Cards cards =
        cardsRepository
            .findByCardNumber(cardsDto.cardNumber())
            .orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.cardNumber()));
    CardsMapper.mapToCardsEntity(cardsDto);
    cardsRepository.save(cards);
    return true;
  }

  public boolean deleteCard(String mobileNumber) {
    Cards cards =
        cardsRepository
            .findByCustomerMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
    cardsRepository.deleteById(cards.getCardId());
    return true;
  }
}
