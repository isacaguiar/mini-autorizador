package br.com.isac.domain.service;

import br.com.isac.adapter.persistence.CardEntity;
import br.com.isac.domain.exception.CardAlreadyExistsException;
import br.com.isac.domain.exception.InsufficientFundsException;
import br.com.isac.domain.exception.InvalidCardFormatNumberException;
import br.com.isac.domain.exception.InvalidPasswordException;
import br.com.isac.domain.model.Card;
import br.com.isac.domain.model.Transaction;
import br.com.isac.domain.port.PersistencePort;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BasicService {

  @Autowired
  public PersistencePort persistencePort;

  protected void executeTransaction(CardEntity card, Transaction transaction) {
    card.setBalance(card.getBalance().subtract(transaction.getValue()));
    persistencePort.save(card);
  }

  protected void validatePassword(String senhaCartao, String senhaDataBase) throws InvalidPasswordException {
    if (!senhaDataBase.equals(senhaCartao)) {
      throw new InvalidPasswordException();
    }
  }

  protected void validBalanceForTransaction(BigDecimal balance, BigDecimal valueTransaction) throws InsufficientFundsException {
    if (balance.compareTo(valueTransaction) < 0 ) {
      throw new InsufficientFundsException();
    }
  }

  protected void validateCardCreation(Card card) throws InvalidCardFormatNumberException {
    isNumber(card.getNumber());
    verifyCardAlreadyExists(card.getNumber());
  }

  protected void verifyCardAlreadyExists(String number) throws CardAlreadyExistsException {
    persistencePort.findByNumber(number)
        .ifPresent(c -> {
          throw new CardAlreadyExistsException(number);
        });
  }

  protected void isNumber(String cardNumber) throws InvalidCardFormatNumberException {
    if (!cardNumber.matches("^\\d+$")) {
      throw new InvalidCardFormatNumberException(cardNumber);
    }
  }
}
