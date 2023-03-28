package br.com.isac.domain.controller;

import br.com.isac.domain.controller.request.TransactionRequest;
import br.com.isac.domain.controller.response.TransactionStatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transacoes")
public interface TransactionController {

    @PostMapping
    ResponseEntity<TransactionStatusResponse> executeTransaction(@RequestBody TransactionRequest transactionRequest);
}
