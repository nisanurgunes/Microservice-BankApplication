package com.bank.cards.controller;

import com.bank.cards.constants.CardsConstants;
import com.bank.cards.dto.CardsContactInfoDto;
import com.bank.cards.dto.CardsDto;
import com.bank.cards.dto.ResponseDto;
import com.bank.cards.service.CardsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "CRUD REST APIs for Cards in Microservice",
    description = "CRUD REST APIs in Microservice to CREATE, UPDATE, FETCH AND DELETE card details")
@RestController
@RequestMapping(
    path = "/api",
    produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardsController {

  private final CardsService cardsService;
  private final CardsContactInfoDto cardsContactInfoDto;
  private final Environment environment;

  @Value("${build.version:1.0.0-default}")
  private String buildVersion;

  public CardsController(
      CardsService cardsService, CardsContactInfoDto cardsContactInfoDto, Environment environment) {
    this.cardsService = cardsService;
    this.cardsContactInfoDto = cardsContactInfoDto;
    this.environment = environment;
  }

  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createCard(
      @Valid
          @RequestParam("mobileNumber")
          @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    cardsService.createCard(mobileNumber);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
  }

  @GetMapping("/fetch")
  public ResponseEntity<CardsDto> fetchCardDetails(
      @RequestParam("mobileNumber")
          @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    CardsDto cardsDto = cardsService.fetchCard(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
  }

  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {
    boolean isUpdated = cardsService.updateCard(cardsDto);
    if (isUpdated) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteCardDetails(
      @RequestParam("mobileNumber")
          @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
          String mobileNumber) {
    boolean isDeleted = cardsService.deleteCard(mobileNumber);
    if (isDeleted) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
    }
  }

  @GetMapping("/build-info")
  public ResponseEntity<String> getBuildInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
  }

  @GetMapping("/java-version")
  public ResponseEntity<String> getJavaVersion() {
    return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
  }

  @GetMapping("/contact-info")
  public ResponseEntity<CardsContactInfoDto> getContactInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(cardsContactInfoDto);
  }
}
