package br.com.isac.adapter.persistence;

import static org.mockito.Mockito.*;

import br.com.isac.util.*;
import java.math.*;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersistenceAdapterTest {

  PersistenceAdapter persistenceAdapter;

  @BeforeAll
  public void loadMocks() {
    persistenceAdapter = spy(PersistenceAdapter.class);
    persistenceAdapter.cardRepository = spy(CardRepository.class);
  }

  @Test
  void createCard() {
    String number = "59874515456954874";
    String password = "123456";
    BigDecimal balance = new BigDecimal(500);
    CardEntity cardEntity = BuilderUtil.cardEntity(number, password, balance);
    persistenceAdapter.save(cardEntity);
    verify(persistenceAdapter.cardRepository).save(cardEntity);
  }

  @Test
  void findByNumber() {
    String number = "59874589856954874";
    persistenceAdapter.findByNumber(number);
    verify(persistenceAdapter.cardRepository).findByNumber(number);
  }

  @Test
  void save() {
    String number = "59874585256954874";
    String password = "123456";
    BigDecimal balance = new BigDecimal(20);
    CardEntity cardEntity = BuilderUtil.cardEntity(1L, number, password, balance);
    persistenceAdapter.save(cardEntity);
    verify(persistenceAdapter.cardRepository).save(cardEntity);
  }

}