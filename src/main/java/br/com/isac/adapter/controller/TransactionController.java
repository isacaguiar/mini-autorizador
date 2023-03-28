package br.com.isac.adapter.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import br.com.isac.adapter.controller.request.TransactionRequest;
import br.com.isac.adapter.controller.response.TransactionStatusResponse;
import br.com.isac.domain.exception.CardNotFoundException;
import br.com.isac.domain.exception.InsufficientFundsException;
import br.com.isac.domain.exception.InvalidPasswordException;
import br.com.isac.domain.service.TransactionService;
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

  @Autowired
  private TransactionService transactionService;

  @PostMapping
  ResponseEntity<String> executeTransaction(@RequestBody TransactionRequest transactionRequest) {
    String response;
    HttpStatus httpStatus = UNPROCESSABLE_ENTITY;
    try {
      response = transactionService.executeTransaction(transactionRequest.toModel());
      httpStatus = OK;
    } catch (InsufficientFundsException e) {
      response = TransactionStatusResponse.INSUFFICIENT_FUNDS;
    } catch (InvalidPasswordException e) {
      response = TransactionStatusResponse.INVALID_PASSWORD;
    } catch (CardNotFoundException e) {
      response = TransactionStatusResponse.CARD_NOT_FOUND;
    }
    return new ResponseEntity<>(response, httpStatus);
  }
}
