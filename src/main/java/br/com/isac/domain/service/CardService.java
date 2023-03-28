package br.com.isac.domain.service;

import br.com.isac.adapter.controller.response.CardResponse;
import br.com.isac.adapter.persistence.CardEntity;
import br.com.isac.domain.model.Card;
import br.com.isac.domain.port.PersistencePort;
import org.springframework.beans.factory.annotation.Autowired;

public class CardService {

    @Autowired
    PersistencePort persistencePort;

    public CardResponse createCard(Card card) {
        CardEntity cardEntity = CardEntity.builder()
                        .number(card.getNumber()).password(card.getPassword()).build();

        cardEntity = persistencePort.createCard(cardEntity);

        CardResponse cardResponse = CardResponse.builder()
                .numeroCartao(cardEntity.getNumber()).senha(cardEntity.getPassword()).build();

        return cardResponse;
    }
}
