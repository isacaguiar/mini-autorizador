package br.com.isac.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.isac.adapter.controller.response.*;
import br.com.isac.adapter.persistence.*;
import br.com.isac.domain.vo.*;
import br.com.isac.domain.port.PersistencePort;
import br.com.isac.util.*;
import java.math.*;
import java.util.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CardServiceTest {

  CardService cardService;

  @BeforeAll
  public void loadMocks() {
    cardService = spy(CardService.class);
    cardService.persistencePort = spy(PersistencePort.class);
  }

  @Test
  void createCard() {
    String number = "6548965878541254";
    String paswword = "123456";
    BigDecimal balance = new BigDecimal(500);
    Card card = BuilderUtil.card(number, paswword, balance);
    CardEntity cardEntity = BuilderUtil.cardEntity(number, paswword, balance);

    when(cardService.persistencePort.save(any())).thenReturn(cardEntity);
    CardResponse cardResponse = cardService.createCard(card);
    assertEquals(cardResponse.getNumeroCartao(), cardEntity.getNumber());
  }

  @Test
  void getBalanceWhenSuccess() {
    String cardNumber = "1235698574582145";
    BigDecimal value = new BigDecimal(500);
    Optional<CardEntity> optionalCardEntity =
        Optional.ofNullable(CardEntity.builder()
            .number("1235698574582145").password("123456").balance(value).build());
    when(cardService.persistencePort.findByNumber(any())).thenReturn(optionalCardEntity);

    BigDecimal retorno = cardService.getBalance(cardNumber);

    assertEquals(retorno, value);
    verify(cardService.persistencePort).findByNumber(cardNumber);
  }
}