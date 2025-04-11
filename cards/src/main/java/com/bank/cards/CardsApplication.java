package com.bank.cards;

import com.bank.cards.dto.CardsContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(info = @Info(title = "Cards Microservice Rest Api Documentation"))
@EnableConfigurationProperties(CardsContactInfoDto.class)
@SpringBootApplication
public class CardsApplication {

  public static void main(String[] args) {
    SpringApplication.run(CardsApplication.class, args);
  }
}
