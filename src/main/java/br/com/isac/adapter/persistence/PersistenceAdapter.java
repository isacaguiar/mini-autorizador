package br.com.isac.adapter.persistence;

import br.com.isac.domain.port.PersistencePort;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

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
    public boolean transactionAuthorization(Integer number, Integer password, BigDecimal value) {
        return false;
    }
}
