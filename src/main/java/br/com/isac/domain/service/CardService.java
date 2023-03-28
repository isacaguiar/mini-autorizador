package br.com.isac.domain.service;

import br.com.isac.adapter.controller.response.CardResponse;
import br.com.isac.adapter.persistence.CardEntity;
import br.com.isac.domain.exception.CardAlreadyExistsException;
import br.com.isac.domain.exception.CardNotFoundException;
import br.com.isac.domain.exception.InvalidCardFormatNumberException;
import br.com.isac.domain.model.Card;
import br.com.isac.domain.port.PersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardService extends BasicService {

  public CardResponse createCard(Card card) throws InvalidCardFormatNumberException, CardAlreadyExistsException {

    validateCardCreation(card);

    CardEntity cardEntity = CardEntity.builder()
        .number(card.getNumber())
        .password(card.getPassword())
        .balance(new BigDecimal(500)).build();

    cardEntity = persistencePort.createCard(cardEntity);

    CardResponse cardResponse = CardResponse.builder()
        .numeroCartao(cardEntity.getNumber()).senha(cardEntity.getPassword()).build();

    return cardResponse;
  }

  public BigDecimal getBalance(String number) throws CardNotFoundException {

    isNumber(number);

    BigDecimal balance = persistencePort.findByNumber(number).map(CardEntity::getBalance).orElseThrow(CardNotFoundException::new);

    return balance;
  }




}
