package com.bank.cards.dto;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponseDto {

  private String message; // Hata açıklaması
  private String path; // Hangi endpoint çağrıldı
  private HttpStatus status; // HTTP status kodu
  private LocalDateTime timestamp; // Zaman

  public ErrorResponseDto(String message, String path, HttpStatus status, LocalDateTime timestamp) {
    this.message = message;
    this.path = path;
    this.status = status;
    this.timestamp = timestamp;
  }
}
