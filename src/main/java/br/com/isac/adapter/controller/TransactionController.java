package br.com.isac.adapter.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import br.com.isac.adapter.controller.request.TransactionRequest;
import br.com.isac.adapter.controller.response.TransactionStatusResponse;
import br.com.isac.domain.exception.CardNotFoundException;
import br.com.isac.domain.exception.InsufficientFundsException;
import br.com.isac.domain.exception.InvalidPasswordException;
import br.com.isac.domain.exception.LockedTransactionException;
import br.com.isac.domain.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transacoes")
public class TransactionController {

  private static final Logger logger = LogManager.getLogger(TransactionController.class);

  @Autowired
  private TransactionService transactionService;

  @PostMapping
  ResponseEntity<String> executeTransaction(@RequestBody TransactionRequest transactionRequest) {
    String response;
    HttpStatus httpStatus = UNPROCESSABLE_ENTITY;
    logger.info("Transaction request");
    try {
      response = transactionService.executeTransaction(transactionRequest.toModel());
      httpStatus = OK;
      logger.info("Executed transaction");
    } catch (InsufficientFundsException e) {
      response = TransactionStatusResponse.INSUFFICIENT_FUNDS;
      logger.error("Insufficient funds");
    } catch (InvalidPasswordException e) {
      response = TransactionStatusResponse.INVALID_PASSWORD;
      logger.error("Invalid password");
    } catch (CardNotFoundException e) {
      response = TransactionStatusResponse.CARD_NOT_FOUND;
      logger.error("Card not found");
    } catch (LockedTransactionException e) {
      response = TransactionStatusResponse.LOCKED_TRANSACTION;
      logger.error("Locked transaction");
    }
    return new ResponseEntity<>(response, httpStatus);
  }
}
