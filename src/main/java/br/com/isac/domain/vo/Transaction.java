package br.com.isac.domain.vo;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

  private String cardNumber;

  private String password;

  private BigDecimal value;

}
