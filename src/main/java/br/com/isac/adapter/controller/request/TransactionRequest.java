package br.com.isac.adapter.controller.request;

import br.com.isac.domain.vo.Transaction;
import lombok.*;

import java.math.BigDecimal;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
  private String numeroCartao;
  private String senhaCartao;
  private BigDecimal valor;
  public Transaction toModel() {
    return Transaction.builder()
        .cardNumber(numeroCartao)
        .password(senhaCartao)
        .value(valor).build();
  }

}
