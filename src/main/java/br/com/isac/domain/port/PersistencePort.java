package br.com.isac.domain.port;

import br.com.isac.adapter.persistence.CardEntity;

import java.util.Optional;

public interface PersistencePort {

  CardEntity createCard(CardEntity cardEntity);

  Optional<CardEntity> findByNumber(String number);

  void save(CardEntity cardEntity);
}
