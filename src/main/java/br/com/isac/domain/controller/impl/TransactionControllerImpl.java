package br.com.isac.domain.controller.impl;

import br.com.isac.domain.controller.TransactionController;
import br.com.isac.domain.controller.request.TransactionRequest;
import br.com.isac.domain.controller.response.TransactionStatusResponse;
import org.springframework.http.ResponseEntity;

public class TransactionControllerImpl implements TransactionController {
    @Override
    public ResponseEntity<TransactionStatusResponse> executeTransaction(TransactionRequest transactionRequest) {
        return null;
    }
}
