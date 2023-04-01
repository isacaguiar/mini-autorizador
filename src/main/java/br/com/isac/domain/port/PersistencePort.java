package br.com.isac.domain.port;

import br.com.isac.adapter.persistence.CardEntity;

import java.util.Optional;

public interface PersistencePort {

  CardEntity save(CardEntity cardEntity);

  Optional<CardEntity> findByNumber(String number);

  Optional<CardEntity> findByNumberAndPassword(String number, String password);

}
