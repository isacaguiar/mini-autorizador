package br.com.isac.adapter.controller.request;

import br.com.isac.domain.model.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

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
