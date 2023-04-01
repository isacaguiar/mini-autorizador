package br.com.isac.domain.service;

import br.com.isac.adapter.controller.response.TransactionStatusResponse;
import br.com.isac.adapter.persistence.CardEntity;
import br.com.isac.domain.exception.CardAlreadyExistsException;
import br.com.isac.domain.exception.CardNotFoundException;
import br.com.isac.domain.exception.InvalidCardFormatNumberException;
import br.com.isac.domain.exception.LockedTransactionException;
import br.com.isac.domain.vo.Transaction;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends BasicService {

  private static final Logger logger = LogManager.getLogger(TransactionService.class);
  public String executeTransaction(Transaction transaction)
      throws InvalidCardFormatNumberException, CardAlreadyExistsException, CardNotFoundException, LockedTransactionException {

    isNumber(transaction.getCardNumber());

    CardEntity cardEntity = getCard(transaction.getCardNumber());
    validatePassword(transaction.getCardNumber(), transaction.getPassword());
    validBalanceForTransaction(cardEntity.getBalance(), transaction.getValue());
    validLockedTransaction(transaction.getCardNumber());
    redisPort.block(transaction.getCardNumber());
    executeTransaction(cardEntity, transaction);
    redisPort.unlock(transaction.getCardNumber());
/*
    persistencePort.findByNumber(transaction.getCardNumber())
        .ifPresentOrElse(card -> {
          validatePassword(transaction.getCardNumber(), transaction.getPassword());
          validBalanceForTransaction(card.getBalance(), transaction.getValue());
          validLockedTransaction(transaction.getCardNumber());
          redisPort.block(transaction.getCardNumber());
          executeTransaction(card, transaction);
          redisPort.unlock(transaction.getCardNumber());
        }, () -> {
          logger.error("Card not found");
          throw new CardNotFoundException();
        });*/

    logger.error("Executed transaction");
    return TransactionStatusResponse.OK;
  }
}
