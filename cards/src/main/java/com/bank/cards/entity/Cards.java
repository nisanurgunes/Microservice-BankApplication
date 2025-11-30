package com.bank.cards.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cards extends BaseEntity {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "card_Id")
  private String cardId;

  @Column(name = "customer_mobile_number")
  private String customerMobileNumber;

  @Column(name = "card_number")
  private String cardNumber;

  @Enumerated(EnumType.STRING)
  private CardType cardType = CardType.VIRTUAL;

  @Column(name = "total_limit")
  private BigDecimal totalLimit;

  @Column(name = "amount_used")
  private BigDecimal amountUsed;

  @Column(name = "available_amount")
  private BigDecimal availableAmount;

  public enum CardType {
    DEBIT,
    CREDIT,
    PREPAID,
    VIRTUAL,
    BUSINESS,
    STUDENT
  }

  public String getCardId() {
    return cardId;
  }

  public String getCustomerMobileNumber() {
    return customerMobileNumber;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public CardType getCardType() {
    return cardType;
  }

  public BigDecimal getTotalLimit() {
    return totalLimit;
  }

  public BigDecimal getAmountUsed() {
    return amountUsed;
  }

  public BigDecimal getAvailableAmount() {
    return availableAmount;
  }

  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  public void setCustomerMobileNumber(String customerMobileNumber) {
    this.customerMobileNumber = customerMobileNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public void setCardType(CardType cardType) {
    this.cardType = cardType;
  }

  public void setTotalLimit(BigDecimal totalLimit) {
    this.totalLimit = totalLimit;
  }

  public void setAmountUsed(BigDecimal amountUsed) {
    this.amountUsed = amountUsed;
  }

  public void setAvailableAmount(BigDecimal availableAmount) {
    this.availableAmount = availableAmount;
  }
}
