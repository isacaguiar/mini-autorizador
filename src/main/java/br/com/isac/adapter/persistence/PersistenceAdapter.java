package br.com.isac.adapter.persistence;

import br.com.isac.domain.port.PersistencePort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PersistenceAdapter implements PersistencePort {

  private static final Logger logger = LogManager.getLogger(PersistenceAdapter.class);
  @Autowired
  CardRepository cardRepository;

  @Override
  public CardEntity save(CardEntity cardEntity) {
    logger.info("Save cart");
    return cardRepository.save(cardEntity);
  }

  @Override
  public Optional<CardEntity> findByNumber(String number) {
    logger.info("Find card by number");
    return cardRepository.findByNumber(number);
  }

  @Override
  public Optional<CardEntity> findByNumberAndPassword(String cardNumber, String password) {
    logger.info("Find card by number");
    return cardRepository.findByNumberAndPassword(cardNumber, password);
  }

}
