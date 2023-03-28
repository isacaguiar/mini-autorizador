package br.com.isac.domain.controller.request;

import br.com.isac.domain.model.Card;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardRequest {

  private String numberCard;

  private String password;

  public Card toModel() {
    return Card.builder().number(numberCard).password(password).build();
  }

}
