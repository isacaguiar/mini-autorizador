package br.com.isac.domain.controller;

import br.com.isac.domain.controller.response.CardResponse;
import br.com.isac.domain.exception.CardAlreadyExistsException;
import br.com.isac.domain.exception.InvalidCardFormatNumberException;
import br.com.isac.domain.service.CardService;
import br.com.isac.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({CardController.class})
@AutoConfigureMockMvc
class CardControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    CardService cardService;

    String path = "/cartoes";

    @Test
    public void shouldThrowCreateCardWhenInvalidCardFormatNumberException() throws Exception {
        when(cardService.createCard(any())).thenThrow(InvalidCardFormatNumberException.class);

        mvc.perform(
                        post(path)
                                .content(FileUtil.loadRequest("create_card"))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldThrowCreateCardWhenCardAlreadyExistsException() throws Exception {



        when(cardService.createCard(any())).thenThrow(CardAlreadyExistsException.class);

        mvc.perform(
                        post(path)
                                .content(FileUtil.loadRequest("create_card"))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void shouldCreateCardWithSuccess() throws Exception {
        CardResponse cardResponse = CardResponse.builder()
                .numeroCartao("123").senha("123").build();
        when(cardService.createCard(any())).thenReturn(cardResponse);

        mvc.perform(
                        post(path)
                                .content(FileUtil.loadRequest("create_card"))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

}