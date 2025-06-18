package com.bank.cards.dto;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(prefix = "cards")
@Getter
@Setter
public class CardsContactInfoDto {
  private String message;
  private Map<String, String> contactDetails;
  private List<String> onCallSupport;
}
