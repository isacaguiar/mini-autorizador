package br.com.isac.domain.service;

import br.com.isac.adapter.persistence.CardEntity;
import br.com.isac.domain.exception.CardAlreadyExistsException;
import br.com.isac.domain.exception.CardNotFoundException;
import br.com.isac.domain.exception.InsufficientFundsException;
import br.com.isac.domain.exception.InvalidCardFormatNumberException;
import br.com.isac.domain.exception.InvalidPasswordException;
import br.com.isac.domain.exception.LockedTransactionException;
import br.com.isac.domain.port.RedisPort;
import br.com.isac.domain.vo.Card;
import br.com.isac.domain.vo.Transaction;
import br.com.isac.domain.port.PersistencePort;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BasicService {

  private static final Logger logger = LogManager.getLogger(BasicService.class);

  @Autowired
  protected PersistencePort persistencePort;

  @Autowired
  protected RedisPort redisPort;

  protected void executeTransaction(CardEntity card, Transaction transaction) {
    card.setBalance(card.getBalance().subtract(transaction.getValue()));
    persistencePort.save(card);
    logger.info("Execute Transaction");
  }

  protected void validatePassword(String numeroCartao, String senhaCartao, String senhaDataBase) throws InvalidPasswordException {
    persistencePort.findByNumberAndPassword(numeroCartao, senhaCartao)
        .ifPresentOrElse(c -> {
          logger.info("Valid password -> {}", numeroCartao);
        }, () -> {
          logger.error("Invalid password -> {}", numeroCartao);
          throw new InvalidPasswordException();
        });
  }

  protected void validBalanceForTransaction(BigDecimal balance, BigDecimal valueTransaction) throws InsufficientFundsException {
    if (balance.compareTo(valueTransaction) < 0 ) {
      logger.info("Insufficient funds");
      throw new InsufficientFundsException();
    }
  }

  protected void validateCardCreation(Card card) throws InvalidCardFormatNumberException {
    isNumber(card.getNumber());
    verifyCardAlreadyExists(card.getNumber());
  }

  protected void verifyCardExists(String number) throws CardAlreadyExistsException {
    persistencePort.findByNumber(number)
        .ifPresentOrElse(c -> {
          logger.error("Card exists -> {}", number);
        }, () -> {
          logger.error("Card not found -> {}", number);
          throw new CardNotFoundException();
        } );
  }
  protected void verifyCardAlreadyExists(String number) throws CardAlreadyExistsException {
    persistencePort.findByNumber(number)
        .ifPresent(c -> {
          logger.error("Card already exists -> {}", number);
          throw new CardAlreadyExistsException(number);
        });
  }

  protected void isNumber(String cardNumber) throws InvalidCardFormatNumberException {
    try {
      new BigInteger(cardNumber);
    } catch (NumberFormatException e) {
      logger.error("Invalid card -> {}", cardNumber);
      throw new InvalidCardFormatNumberException(cardNumber);
    }
  }

  protected void validLockedTransaction(String key) throws LockedTransactionException {
    try {
      Optional<String> optional = Optional.of(redisPort.get(key));
      optional.ifPresent(c -> {
        logger.error("Locked transaction for card -> {}", key);
        throw new LockedTransactionException();
      });
    } catch (NullPointerException e) {
      logger.info("Authorized transaction for card -> {}", key);
    }
  }
}
