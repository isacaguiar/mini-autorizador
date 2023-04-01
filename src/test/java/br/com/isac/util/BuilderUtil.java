package br.com.isac.util;

import br.com.isac.adapter.controller.response.CardResponse;
import br.com.isac.adapter.persistence.*;
import br.com.isac.domain.vo.*;
import java.math.*;
import java.util.*;

public class BuilderUtil {

  public static Card card(String number, String password, BigDecimal balance) {
    return Card.builder()
        .id(new Date().getTime())
        .number(number)
        .password(password)
        .balance(balance)
        .build();
  }

  public static CardEntity cardEntity(String number, String password, BigDecimal balance) {
    return cardEntity(new Date().getTime(), number, password, balance);
  }

  public static CardEntity cardEntity(Long id, String number, String password, BigDecimal balance) {
    return CardEntity.builder()
        .id(id)
        .number(number)
        .password(password)
        .balance(balance)
        .build();
  }

  public static Transaction transaction(String cardNumber, String password, BigDecimal value) {
    return Transaction.builder()
        .cardNumber(cardNumber)
        .password(password)
        .value(value)
        .build();
  }

  public static Optional<CardEntity> optionalCardEntity(String number, String password, BigDecimal balance) {
     return
        Optional.ofNullable(CardEntity.builder()
            .number(number).password(password).balance(balance).build());
  }

  public static CardResponse cardResponse(String number, String password) {
    return
        CardResponse.builder()
            .numeroCartao(number).senha(password).build();
  }


}
