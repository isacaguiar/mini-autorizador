package br.com.isac.adapter.controller.request;

import br.com.isac.domain.vo.Card;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CardRequest {

  private String numeroCartao;

  private String senha;

  public Card toModel() {
    return Card.builder().number(numeroCartao).password(senha).build();
  }

}
