package br.com.isac.adapter.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.isac.adapter.controller.response.TransactionStatusResponse;
import br.com.isac.domain.exception.CardNotFoundException;
import br.com.isac.domain.exception.InsufficientFundsException;
import br.com.isac.domain.exception.InvalidPasswordException;
import br.com.isac.domain.service.TransactionService;
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

@ExtendWith(SpringExtension.class)
@WebMvcTest({TransactionController.class})
@AutoConfigureMockMvc
class TransactionControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  TransactionService transactionService;

  final String PATH = "/transacoes";

  @Test
  public void shouldExecuteTransactionWithSuccess() throws Exception {
    String response = TransactionStatusResponse.OK;
    when(transactionService.executeTransaction(any())).thenReturn(response);

    mvc.perform(
            post(PATH)
                .content(FileUtil.loadRequest("execute_transaction"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void shouldThrowCardNotFoundExceptionWhenExecuteTransaction() throws Exception {
    when(transactionService.executeTransaction(any())).thenThrow(CardNotFoundException.class);
    mvc.perform(
            post(PATH)
                .content(FileUtil.loadRequest("execute_transaction"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  public void shouldThrowInsufficientFundsExceptionWhenExecuteTransaction() throws Exception {
    when(transactionService.executeTransaction(any())).thenThrow(InsufficientFundsException.class);
    mvc.perform(
            post(PATH)
                .content(FileUtil.loadRequest("execute_transaction"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

  @Test
  public void shouldThrowInvalidPasswordExceptionWhenExecuteTransaction() throws Exception {
    when(transactionService.executeTransaction(any())).thenThrow(InvalidPasswordException.class);
    mvc.perform(
            post(PATH)
                .content(FileUtil.loadRequest("execute_transaction"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
  }

}