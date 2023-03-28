package br.com.isac.adapter.controller.impl;

import br.com.isac.domain.controller.CardController;
import br.com.isac.domain.controller.request.CardRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CardControllerImplTest {

    @MockBean
    CardController cardController;

    @Test
    public void whenCreateCardWithSuccess() {
        CardRequest request =
                CardRequest.builder()
                        .numberCard("123456789")
                        .password("123456").build();
        cardController.createCard(request);
    }

}