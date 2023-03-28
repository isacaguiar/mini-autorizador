package br.com.isac.domain.port;

import br.com.isac.adapter.persistence.CardEntity;

import java.math.BigDecimal;

public interface PersistencePort {

    CardEntity createCard(CardEntity cardEntity);

    BigDecimal getBalance(Integer number);

    boolean transactionAuthorization(Integer number, Integer password, BigDecimal value);
}
