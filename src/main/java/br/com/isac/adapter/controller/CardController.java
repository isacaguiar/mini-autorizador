package br.com.isac.adapter.controller;

import br.com.isac.adapter.controller.request.CardRequest;
import br.com.isac.adapter.controller.response.CardResponse;
import br.com.isac.domain.exception.CardAlreadyExistsException;
import br.com.isac.domain.exception.CardNotFoundException;
import br.com.isac.domain.exception.InvalidCardFormatNumberException;
import br.com.isac.domain.service.BasicService;
import br.com.isac.domain.service.CardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
@RequestMapping(value = "/cartoes")
public class CardController {

  private static final Logger logger = LogManager.getLogger(CardController.class);

  @Autowired
  private CardService cardService;

  @PostMapping
  public ResponseEntity<CardResponse> createCard(@Valid @RequestBody CardRequest createCardRequest) {
    CardResponse response;
    HttpStatus httpStatus = UNPROCESSABLE_ENTITY;
    logger.info("Card creation request");
    try {
      response = cardService.createCard(createCardRequest.toModel());
      httpStatus = CREATED;
      logger.info("Card created -> Number: ".concat(createCardRequest.getNumberCard()));
    } catch (CardAlreadyExistsException e) {
      response = CardResponse.builder()
          .numeroCartao(createCardRequest.getNumberCard()).senha(createCardRequest.getPassword()).build();
      logger.error("Card already exists.");
    } catch (InvalidCardFormatNumberException e) {
      response = null;
      logger.error("Invalid card format number.");
    }
    return new ResponseEntity<>(response, httpStatus);
  }

  @GetMapping(value = "/{numeroCartao}")
  public ResponseEntity<BigDecimal> getBalance(@PathVariable("numeroCartao") String numberCard) {
    BigDecimal response;
    HttpStatus httpStatus = NOT_FOUND;
    logger.info("Get balance request");
    try {
      response = cardService.getBalance(numberCard);
      httpStatus = CREATED;
      logger.info("Get balance request");
    } catch (CardNotFoundException e) {
      response = null;
      logger.error("Card not found.");
    }
    return new ResponseEntity<>(response, httpStatus);
  }

}
