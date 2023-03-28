package br.com.isac.adapter.persistence;

import br.com.isac.domain.model.Transaction;
import br.com.isac.domain.port.PersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PersistenceAdapter implements PersistencePort {

  @Autowired
  CardRepository cardRepository;

  @Override
  public CardEntity createCard(CardEntity cardEntity) {
    return cardRepository.save(cardEntity);
  }

  @Override
  public BigDecimal getBalance(Integer number) {
    return null;
  }

  @Override
  public Optional<CardEntity> findByNumber(String number) {
    Optional<CardEntity> opCard = cardRepository.findByNumber(number);
    return opCard;
  }

  @Override
  public void save(CardEntity cardEntity) {
    cardRepository.save(cardEntity);
  }

}
