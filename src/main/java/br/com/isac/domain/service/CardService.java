package br.com.isac.domain.service;

import br.com.isac.domain.controller.response.CardResponse;
import br.com.isac.adapter.persistence.CardEntity;
import br.com.isac.domain.exception.CardAlreadyExistsException;
import br.com.isac.domain.exception.InvalidCardFormatNumberException;
import br.com.isac.domain.model.Card;
import br.com.isac.domain.port.PersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardService {

    @Autowired
    PersistencePort persistencePort;

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

    private void validateCardCreation(Card card) throws InvalidCardFormatNumberException {
        isNumber(card.getNumber());
        verifyCardAlreadyExists(card.getNumber());
    }

    private void verifyCardAlreadyExists(String number) throws CardAlreadyExistsException {
        persistencePort.findByNumber(number)
                .ifPresent(c -> {
                    throw new CardAlreadyExistsException(number);
                });
    }

    private void isNumber(String cardNumber) throws InvalidCardFormatNumberException {
        if(!cardNumber.matches("^\\d+$"))
            throw new InvalidCardFormatNumberException(cardNumber);
    }
}
