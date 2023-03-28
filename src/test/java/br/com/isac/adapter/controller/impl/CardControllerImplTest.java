package br.com.isac.adapter.controller.impl;

import br.com.isac.adapter.controller.request.CardRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class CardControllerImplTest {

    @MockBean
    CardControllerImpl cardController;

    @Test
    public void whenCreateCardWithSuccess() {
        CardRequest request =
                CardRequest.builder()
                        .numberCard("123456789")
                        .password("123456").build();
        cardController.createCard(request);
    }

}