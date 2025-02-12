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

  // Parametresiz constructor
  public ErrorResponseDto() {
    // Parametresiz constructor işlemleri
  }

  // Parametreli constructor (Önerilen güncelleme)
  public ErrorResponseDto(
      String message, HttpStatus status, LocalDateTime timestamp, String details) {
    this.message = message;
    this.status = status;
    this.timestamp = timestamp;
    this.details = details;
  }

  // Getter ve Setter metodları
}
