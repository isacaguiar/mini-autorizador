package br.com.isac.domain.service;

import br.com.isac.adapter.controller.response.TransactionStatusResponse;
import br.com.isac.domain.exception.CardAlreadyExistsException;
import br.com.isac.domain.exception.CardNotFoundException;
import br.com.isac.domain.exception.InvalidCardFormatNumberException;
import br.com.isac.domain.model.Transaction;

public class TransactionService extends BasicService {

  public String executeTransaction(Transaction transaction)
      throws InvalidCardFormatNumberException, CardAlreadyExistsException, CardNotFoundException {

    isNumber(transaction.getCardNumber());

    persistencePort.findByNumber(transaction.getCardNumber())
        .ifPresentOrElse(card -> {
          validatePassword(transaction.getPassword(), card.getPassword());
          validBalanceForTransaction(card.getBalance(), transaction.getValue());
          executeTransaction(card, transaction);
        }, () -> {
          throw new CardNotFoundException();
        });

    return TransactionStatusResponse.OK;
  }
}
