package br.com.isac.domain.service;

import br.com.isac.adapter.controller.response.CardResponse;
import br.com.isac.adapter.persistence.CardEntity;
import br.com.isac.domain.exception.CardAlreadyExistsException;
import br.com.isac.domain.exception.CardNotFoundException;
import br.com.isac.domain.exception.InvalidCardFormatNumberException;
import br.com.isac.domain.vo.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class CardService extends BasicService {

  private static final Logger logger = LogManager.getLogger(CardService.class);

  public CardResponse createCard(Card card) throws InvalidCardFormatNumberException, CardAlreadyExistsException {

    validateCardCreation(card);

    CardEntity cardEntity = CardEntity.builder()
        .number(card.getNumber())
        .password(card.getPassword())
        .balance(new BigDecimal(500)).build();

    cardEntity = persistencePort.save(cardEntity);
    logger.info("Create card");

    return CardResponse.builder()
        .numeroCartao(cardEntity.getNumber()).senha(cardEntity.getPassword()).build();
  }

  public BigDecimal getBalance(String number) throws CardNotFoundException {
    isNumber(number);
    logger.info("Create card");
    return persistencePort.findByNumber(number).map(CardEntity::getBalance)
        .orElseThrow( () -> {
          logger.info("Card not found");
          return new CardNotFoundException();
        }
    );
  }


}
