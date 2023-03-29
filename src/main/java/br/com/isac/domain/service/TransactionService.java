package br.com.isac.domain.service;

import br.com.isac.adapter.controller.response.TransactionStatusResponse;
import br.com.isac.domain.exception.CardAlreadyExistsException;
import br.com.isac.domain.exception.CardNotFoundException;
import br.com.isac.domain.exception.InvalidCardFormatNumberException;
import br.com.isac.domain.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends BasicService {

  private static final Logger logger = LogManager.getLogger(TransactionService.class);
  public String executeTransaction(Transaction transaction)
      throws InvalidCardFormatNumberException, CardAlreadyExistsException, CardNotFoundException {

    isNumber(transaction.getCardNumber());

    persistencePort.findByNumber(transaction.getCardNumber())
        .ifPresentOrElse(card -> {
          validatePassword(transaction.getPassword(), card.getPassword());
          validBalanceForTransaction(card.getBalance(), transaction.getValue());
          executeTransaction(card, transaction);
        }, () -> {
          logger.error("Card not found");
          throw new CardNotFoundException();
        });

    logger.error("Card created");
    return TransactionStatusResponse.OK;
  }
}
