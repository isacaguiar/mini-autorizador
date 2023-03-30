package br.com.isac.adapter.controller.request;

import br.com.isac.domain.vo.Transaction;
import lombok.*;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class TransactionRequest {
  private String cardNumber;
  private String cardPassword;
  private BigDecimal value;
  public Transaction toModel() {
    return Transaction.builder().cardNumber(cardNumber).password(cardPassword).value(value).build();
  }

}
