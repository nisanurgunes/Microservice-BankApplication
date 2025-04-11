package com.bank.accounts.dto;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponseDto {
  private String message;
  private HttpStatus status;
  private LocalDateTime timestamp;
  private String details;

  public ErrorResponseDto() {}

  public ErrorResponseDto(
      String message, HttpStatus status, LocalDateTime timestamp, String details) {
    this.message = message;
    this.status = status;
    this.timestamp = timestamp;
    this.details = details;
  }
}
