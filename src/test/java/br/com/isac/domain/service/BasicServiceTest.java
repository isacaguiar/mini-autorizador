package br.com.isac.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.isac.adapter.persistence.CardEntity;
import br.com.isac.domain.exception.CardAlreadyExistsException;
import br.com.isac.domain.exception.InsufficientFundsException;
import br.com.isac.domain.exception.InvalidCardFormatNumberException;
import br.com.isac.domain.exception.InvalidPasswordException;
import br.com.isac.domain.exception.LockedTransactionException;
import br.com.isac.domain.port.RedisPort;
import br.com.isac.domain.vo.Card;
import br.com.isac.domain.vo.Transaction;
import br.com.isac.domain.port.PersistencePort;
import br.com.isac.util.BuilderUtil;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BasicServiceTest {

  @MockBean
  BasicService basicService;

  @BeforeAll
  public void loadMocks() {
    basicService = spy(BasicService.class);
    basicService.persistencePort = spy(PersistencePort.class);
    basicService.redisPort = spy(RedisPort.class);
  }

  @Test
  void executeTransactionWhenSuccess() {
    String number = "987654";
    String password = "123";
    CardEntity card = CardEntity.builder()
        .id(1l)
        .number(number)
        .password(password)
        .balance(new BigDecimal(500)).build();
    Transaction transaction = Transaction.builder()
            .cardNumber(number)
            .password(password)
            .value(new BigDecimal(10)).build();
    basicService.executeTransaction(card, transaction);
    verify(basicService.persistencePort, times(1)).save(card);
  }

  @Test
  void validateCardCreationWhenSuccess() {
    Card card = Card.builder()
        .number("123456")
        .password("123456")
        .balance(new BigDecimal(500))
        .build();
    basicService.validateCardCreation(card);

    verify(basicService, times(1)).isNumber(card.getNumber());
    verify(basicService, times(1)).verifyCardAlreadyExists(card.getNumber());
  }

  @Test
  void shouldThrowInvalidCardFormatNumberExceptionWhenVerifyCardAlreadyExists() {
    Card card = Card.builder()
        .number("1234CC")
        .password("123456")
        .balance(new BigDecimal(500))
        .build();

    assertThrows(InvalidCardFormatNumberException.class, () -> {
      basicService.validateCardCreation(card);
    });
  }

  @Test
  void shouldThrowCardAlreadyExistsExceptionWhenVerifyCardAlreadyExists() {
    String number = "1234567";
    CardEntity cardEntity = CardEntity.builder()
        .number(number)
        .password(number)
        .balance(new BigDecimal(500)).build();

    when(basicService.persistencePort.findByNumber(number)).thenReturn(Optional.ofNullable(cardEntity));

    assertThrows(CardAlreadyExistsException.class, () -> {
      basicService.verifyCardAlreadyExists(number);
    });

    verify(basicService, times(1)).verifyCardAlreadyExists(number);
  }

  @Test
  void validBalanceForTransactionWhenSuccess() {
    BigDecimal balance = new BigDecimal(500);
    BigDecimal valueTransaction = new BigDecimal(20);
    basicService.validBalanceForTransaction(balance, valueTransaction);
    verify(basicService, times(1)).validBalanceForTransaction(any(), any());
  }

  @Test
  void shouldThrowInsufficientFundsExceptionWhenValidBalanceForTransaction() {
    BigDecimal balance = new BigDecimal(5);
    BigDecimal valueTransaction = new BigDecimal(10);

    assertThrows(InsufficientFundsException.class, () -> {
      basicService.validBalanceForTransaction(balance, valueTransaction);
    });

    verify(basicService, times(1)).validBalanceForTransaction(balance, valueTransaction);
  }

  @Test
  void validatePasswordWhenSuccess() {
    Long id = 10L;
    String cardNumber = "1111111111111111";
    String password = "password";
    BigDecimal balance = new BigDecimal(500);
    CardEntity cardEntity = BuilderUtil.cardEntity(id, cardNumber, password, balance);
    Optional<CardEntity> optionalCardEntity = Optional.of(cardEntity);
    when(basicService.persistencePort.findByNumberAndPassword(any(), any())).thenReturn(optionalCardEntity);
    basicService.validatePassword(cardNumber, password);
    verify(basicService.persistencePort).findByNumberAndPassword(cardNumber, password);
    verify(basicService).validatePassword(cardNumber, password);
  }

  @Test
  void shouldThrowInvalidPasswordExceptionWhenValidatePassword() {
    String numeroCartao = "222222222222222";
    String password = "password";
    String invalidPassword = "p@ssword";

    assertThrows(InvalidPasswordException.class, () -> {
      basicService.validatePassword(numeroCartao, password);
    });

    verify(basicService, times(1)).validatePassword(any(), any());
  }

  @Test
  void isNumberWhenSuccess() {
    basicService.isNumber("10");
    verify(basicService, times(1)).isNumber(any());
  }

  @Test
  void shouldThrowInvalidCardFormatNumberExceptionWhenIsNumber() {
    String invalidNumber = "ABC";

    assertThrows(InvalidCardFormatNumberException.class, () -> {
      basicService.isNumber(invalidNumber);
    });

    verify(basicService, times(1)).isNumber(invalidNumber);
  }

  @Test
  void shouldThrowLockedTransactionExceptionnWhenValidLockedTransaction() {
    String key = "ABC";

    when(basicService.redisPort.get(any())).thenReturn(key);

    assertThrows(LockedTransactionException.class, () -> {
      basicService.validLockedTransaction(key);
    });

    verify(basicService, times(1)).isNumber(key);
  }

  @Test
  void validLockedTransactionWhenSuccess() {
    String key = "123";

    basicService.validLockedTransaction(key);

    verify(basicService).validLockedTransaction(key);
  }
}