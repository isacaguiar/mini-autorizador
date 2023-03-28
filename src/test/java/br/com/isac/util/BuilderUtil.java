package br.com.isac.util;

import br.com.isac.adapter.persistence.*;
import br.com.isac.domain.model.*;
import java.math.*;

public class BuilderUtil {

  public static Card car(String number, String password, BigDecimal balance) {
    return Card.builder()
        .number(number)
        .password(password)
        .balance(balance)
        .build();
  }

  public static CardEntity cardEntity(String number, String password, BigDecimal balance) {
    return CardEntity.builder()
        .number(number)
        .password(password)
        .balance(balance)
        .build();
  }
}
