package br.com.isac.adapter.controller;

import br.com.isac.adapter.controller.request.TransactionRequest;
import br.com.isac.adapter.controller.response.TransactionStatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/transacoes")
public interface TransactionController {

    @PostMapping
    ResponseEntity<TransactionStatusResponse> executeTransaction(@RequestBody TransactionRequest transactionRequest);
}
