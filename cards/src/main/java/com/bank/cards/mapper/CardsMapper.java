package com.bank.cards.mapper;

import com.bank.cards.dto.CardsDto;
import com.bank.cards.entity.Cards;

public class CardsMapper {

  public static CardsDto mapToCardsDto(Cards cards) {
    return new CardsDto(
        cards.getCustomerMobileNumber(),
        cards.getCardNumber(),
        cards.getCardType().name(),
        cards.getTotalLimit(),
        cards.getAmountUsed(),
        cards.getAvailableAmount());
  }

  public static Cards mapToCardsEntity(CardsDto cardsDto) {
    Cards cards = new Cards();
    cards.setCardNumber(cardsDto.cardNumber());
    cards.setCardType(Cards.CardType.valueOf(cardsDto.cardType()));
    cards.setCustomerMobileNumber(cardsDto.mobileNumber());
    cards.setTotalLimit(cardsDto.totalLimit());
    cards.setAvailableAmount(cardsDto.availableAmount());
    cards.setAmountUsed(cardsDto.amountUsed());
    return cards;
  }
}
