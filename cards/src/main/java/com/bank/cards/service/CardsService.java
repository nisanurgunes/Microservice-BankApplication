package com.bank.cards.service;

import com.bank.cards.constants.CardsConstants;
import com.bank.cards.dto.CardsDto;
import com.bank.cards.entity.Cards;
import com.bank.cards.exception.CardAlreadyExistsException;
import com.bank.cards.exception.ResourceNotFoundException;
import com.bank.cards.mapper.CardsMapper;
import com.bank.cards.repository.CardsRepository;
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
    Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
    if (optionalCards.isPresent()) {
      throw new CardAlreadyExistsException(
          "Card already registered with given mobileNumber " + mobileNumber);
    }
    cardsRepository.save(createNewCard(mobileNumber));
  }

  private Cards createNewCard(String mobileNumber) {
    Cards newCard = new Cards();
    long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
    newCard.setCardNumber(Long.toString(randomCardNumber));
    newCard.setMobileNumber(mobileNumber);
    newCard.setCardType(CardsConstants.CREDIT_CARD);
    newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
    newCard.setAmountUsed(0);
    newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
    newCard.setCreatedBy("SYSTEM");
    newCard.setCreatedAt(LocalDateTime.now());

    return newCard;
  }

  public CardsDto fetchCard(String mobileNumber) {
    Cards cards =
        cardsRepository
            .findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
    return CardsMapper.mapToCardsDto(cards, new CardsDto());
  }

  public boolean updateCard(CardsDto cardsDto) {
    Cards cards =
        cardsRepository
            .findByCardNumber(cardsDto.getCardNumber())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
    CardsMapper.mapToCards(cardsDto, cards);
    cardsRepository.save(cards);
    return true;
  }

  public boolean deleteCard(String mobileNumber) {
    Cards cards =
        cardsRepository
            .findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
    cardsRepository.deleteById(cards.getCardId());
    return true;
  }
}
