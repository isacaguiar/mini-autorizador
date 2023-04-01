package br.com.isac.adapter.controller;

import br.com.isac.adapter.controller.CardController;
import br.com.isac.adapter.controller.response.CardResponse;
import br.com.isac.domain.exception.CardAlreadyExistsException;
import br.com.isac.domain.exception.CardNotFoundException;
import br.com.isac.domain.exception.InvalidCardFormatNumberException;
import br.com.isac.domain.service.CardService;
import br.com.isac.domain.vo.Card;
import br.com.isac.util.BuilderUtil;
import br.com.isac.util.FileUtil;
import java.math.BigDecimal;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

  final String PATH = "/cartoes";
  final String PATH_BALANCE = "/cartoes/10";

  @Test
  void shouldGetBalanceWithSuccess() throws Exception {
    BigDecimal value = new BigDecimal(500);
    when(cardService.getBalance(any())).thenReturn(value);

    mvc.perform(
            get(PATH_BALANCE)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  void shouldThrowCardNotFoundExceptionWhenGetBalance() throws Exception {
    when(cardService.getBalance(any())).thenThrow(CardNotFoundException.class);
    mvc.perform(
            get(PATH_BALANCE)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  void shouldThrowInvalidCardFormatNumberExceptionWhenCreateCard() throws Exception {
    when(cardService.createCard(any())).thenThrow(InvalidCardFormatNumberException.class);
    mvc.perform(
            post(PATH)
                .content(FileUtil.loadRequest("create_card"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  void shouldThrowCardAlreadyExistsExceptionCardWhenCreate() throws Exception {
    when(cardService.createCard(any())).thenThrow(CardAlreadyExistsException.class);
    mvc.perform(
            post(PATH)
                .content(FileUtil.loadRequest("create_card"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  void shouldCreateCardWithSuccess() throws Exception {
    String carNUmber = "1111111111111111";
    String password = "123456";
    BigDecimal balance = new BigDecimal(500);
    CardResponse cardResponse = BuilderUtil.cardResponse(carNUmber, password);
    Card card = BuilderUtil.card(carNUmber, password, balance);
    when(cardService.createCard(any())).thenReturn(cardResponse);

    mvc.perform(
            post(PATH)
                .content(FileUtil.loadRequest("create_card"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }

}