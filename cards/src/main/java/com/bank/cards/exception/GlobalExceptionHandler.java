package com.bank.cards.exception;

import com.bank.cards.dto.ErrorResponseDto;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

    return ResponseEntity.badRequest().body(errors);
  }

  // Card Already Exists – 400
  @ExceptionHandler(CardAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDto> handleCardAlreadyExists(
      CardAlreadyExistsException ex, WebRequest request) {

    ErrorResponseDto response =
        new ErrorResponseDto(
            ex.getMessage(), getPath(request), HttpStatus.BAD_REQUEST, LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  // Resource Not Found – 404
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleResourceNotFound(
      ResourceNotFoundException ex, WebRequest request) {

    ErrorResponseDto response =
        new ErrorResponseDto(
            ex.getMessage(), getPath(request), HttpStatus.NOT_FOUND, LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  // All Other Exceptions – 500
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleGeneralException(Exception ex, WebRequest request) {

    ErrorResponseDto response =
        new ErrorResponseDto(
            ex.getMessage(),
            getPath(request),
            HttpStatus.INTERNAL_SERVER_ERROR,
            LocalDateTime.now());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  private String getPath(WebRequest request) {
    return ((ServletWebRequest) request).getRequest().getRequestURI();
  }
}
