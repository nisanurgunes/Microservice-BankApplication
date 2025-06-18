package com.bank.loans.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Setter
@Getter
@RefreshScope
@ConfigurationProperties(prefix = "loans")

public class LoansContactInfoDto {
  private String message;
  private Map<String, String> contactDetails;
  private List<String> onCallSupport;

}
