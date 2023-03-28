package br.com.isac.adapter.controller.impl;

import br.com.isac.adapter.controller.TransactionController;
import br.com.isac.adapter.controller.request.TransactionRequest;
import br.com.isac.adapter.controller.response.TransactionStatusResponse;
import org.springframework.http.ResponseEntity;

public class TransactionControllerImpl implements TransactionController {
    @Override
    public ResponseEntity<TransactionStatusResponse> executeTransaction(TransactionRequest transactionRequest) {
        return null;
    }
}
