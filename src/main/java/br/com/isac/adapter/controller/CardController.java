package br.com.isac.adapter.controller;

import br.com.isac.adapter.controller.request.CardRequest;
import br.com.isac.adapter.controller.response.CardResponse;
import br.com.isac.domain.exception.CardAlreadyExistsException;
import br.com.isac.domain.exception.CardNotFoundException;
import br.com.isac.domain.exception.InvalidCardFormatNumberException;
import br.com.isac.domain.service.BasicService;
import br.com.isac.domain.service.CardService;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
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
  public ResponseEntity<Object> createCard(@Valid @RequestBody CardRequest createCardRequest) {
    Object response;
    HttpStatus httpStatus = UNPROCESSABLE_ENTITY;
    logger.info("Card creation request");
    try {
      response = cardService.createCard(createCardRequest.toModel());
      httpStatus = CREATED;
      logger.info("Card created -> Number: ".concat(createCardRequest.getNumeroCartao()));
    } catch (CardAlreadyExistsException e) {
      response = CardResponse.builder()
          .numeroCartao(createCardRequest.getNumeroCartao()).senha(createCardRequest.getSenha()).build();
      logger.error("Card already exists");
    } catch (InvalidCardFormatNumberException e) {
      response = "NÚMERO_DE CARTAO_INVÁLIDO";
      logger.error("Invalid card format number");
    }
    return new ResponseEntity<>(response, httpStatus);
  }

  @GetMapping(value = "/{numeroCartao}")
  public ResponseEntity<String> getBalance(@PathVariable("numeroCartao") String numberCard) {
    BigDecimal response;
    HttpStatus httpStatus = NOT_FOUND;
    logger.info("Get balance request");
    try {
      response = cardService.getBalance(numberCard);
      httpStatus = CREATED;
      logger.info("Get balance request");
    } catch (CardNotFoundException e) {
      response = null;
      logger.error("Card not found");
    }
    return new ResponseEntity<>(formaterBigDecimal(response), httpStatus);
  }

  private String formaterBigDecimal(BigDecimal value) {
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    return value == null ? null : new DecimalFormat("###.00", symbols).format(value);
  }

}
