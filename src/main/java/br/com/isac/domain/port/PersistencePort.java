package br.com.isac.domain.port;

import br.com.isac.adapter.persistence.CardEntity;

import br.com.isac.domain.model.Transaction;
import java.math.BigDecimal;
import java.util.Optional;

public interface PersistencePort {

  CardEntity createCard(CardEntity cardEntity);

  BigDecimal getBalance(Integer number);

  Optional<CardEntity> findByNumber(String number);

  void save(CardEntity cardEntity);
}
