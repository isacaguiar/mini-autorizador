package br.com.isac.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.isac.adapter.controller.response.*;
import br.com.isac.adapter.persistence.*;
import br.com.isac.domain.exception.*;
import br.com.isac.domain.vo.*;
import br.com.isac.domain.port.*;
import br.com.isac.util.*;
import java.math.*;
import java.util.*;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionServiceTest {

  TransactionService transactionService;

  @BeforeAll
  public void loadMocks() {
    transactionService = spy(TransactionService.class);
    transactionService.persistencePort = spy(PersistencePort.class);
    transactionService.redisPort = spy(RedisPort.class);
  }

  @Test
  void executeTransactionWhenSucess() {
    String cardNumber = "888888888888888";
    String password = "123456";
    BigDecimal value = new BigDecimal(120);
    Transaction transaction = BuilderUtil.transaction(cardNumber, password, value);
    Optional<CardEntity> optionalCardEntity = BuilderUtil.optionalCardEntity(cardNumber, password, new BigDecimal(500));
    when(transactionService.persistencePort.findByNumber(cardNumber)).thenReturn(optionalCardEntity);
    when(transactionService.persistencePort.findByNumberAndPassword(cardNumber, password)).thenReturn(optionalCardEntity);
    String retorno = transactionService.executeTransaction(transaction);

    assertEquals(TransactionStatusResponse.OK, retorno);
    verify(transactionService.persistencePort, times(1)).findByNumber(cardNumber);
    verify(transactionService.persistencePort, times(1)).findByNumberAndPassword(cardNumber, password);
  }

  @Test
  void shouldThrowInvalidPasswordExceptionWhenSucessExecuteTransaction() throws CardNotFoundException {
    String cardNumber = "5856985412547854";
    String password = "123456";
    BigDecimal value = new BigDecimal(120);
    Transaction transaction = BuilderUtil.transaction(cardNumber, password, value);
    Optional<CardEntity> optionalCardEntity = BuilderUtil.optionalCardEntity(cardNumber, password, new BigDecimal(500));
    when(transactionService.persistencePort.findByNumber(any())).thenReturn(optionalCardEntity);

    assertThrows(InvalidPasswordException.class, () -> {
      transactionService.executeTransaction(transaction);
    });
  }

  @Test
  void shouldThrowCardNotFoundExceptionWhenSucessExecuteTransaction() throws CardNotFoundException {
    String cardNumber = "5856981212547857";
    String password = "123456";
    BigDecimal value = new BigDecimal(120);
    Transaction transaction = BuilderUtil.transaction(cardNumber, password, value);
    when(transactionService.persistencePort.findByNumber(any())).thenReturn(Optional.empty());

    assertThrows(CardNotFoundException.class, () -> {
      transactionService.executeTransaction(transaction);
    });
  }
}