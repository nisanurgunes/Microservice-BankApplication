package com.bank.loans.dto;

import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(prefix = "loans")
@Data
public class LoansContactInfoDto {
  private String message;
  private Map<String, String> contactDetails;
  private List<String> onCallSupport;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Map<String, String> getContactDetails() {
    return contactDetails;
  }

  public void setContactDetails(Map<String, String> contactDetails) {
    this.contactDetails = contactDetails;
  }

  public List<String> getOnCallSupport() {
    return onCallSupport;
  }

  public void setOnCallSupport(List<String> onCallSupport) {
    this.onCallSupport = onCallSupport;
  }
}
