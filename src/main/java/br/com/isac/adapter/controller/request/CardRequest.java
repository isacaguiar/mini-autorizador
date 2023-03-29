package br.com.isac.adapter.controller.request;

import br.com.isac.domain.model.Card;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CardRequest {

  private String numberCard;

  private String password;

  public Card toModel() {
    return Card.builder().number(numberCard).password(password).build();
  }

}
