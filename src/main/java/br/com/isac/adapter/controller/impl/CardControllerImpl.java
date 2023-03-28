package br.com.isac.adapter.controller.impl;

import br.com.isac.adapter.controller.CardController;
import br.com.isac.adapter.controller.request.CardRequest;
import br.com.isac.adapter.controller.response.CardResponse;
import br.com.isac.domain.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.*;

import java.math.BigDecimal;

public class CardControllerImpl implements CardController {

    @Autowired
    CardService cardService;
    @Override
    public ResponseEntity<CardResponse> createCard(CardRequest createCardRequest) {
        CardResponse response =  cardService.createCard(createCardRequest.toModel());
        return new ResponseEntity<>(response, CREATED);
    }

    @Override
    public ResponseEntity<BigDecimal> getBalance(String numberCard) {
        return null;
    }
}
