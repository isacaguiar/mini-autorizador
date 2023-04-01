package br.com.isac.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {
  Optional<CardEntity> findByNumber(String number);

  Optional<CardEntity> findByNumberAndPassword(String number, String password);

}
