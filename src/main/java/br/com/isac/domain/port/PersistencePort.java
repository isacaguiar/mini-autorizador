package br.com.isac.domain.port;

import br.com.isac.adapter.persistence.CardEntity;

import java.math.BigDecimal;
import java.util.Optional;

public interface PersistencePort {

    CardEntity createCard(CardEntity cardEntity);

    BigDecimal getBalance(Integer number);

    Optional<CardEntity> findByNumber(String number);

    boolean transactionAuthorization(Integer number, Integer password, BigDecimal value);
}
