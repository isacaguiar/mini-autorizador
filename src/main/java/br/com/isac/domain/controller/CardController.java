package br.com.isac.domain.controller;

import br.com.isac.domain.controller.request.CardRequest;
import br.com.isac.domain.controller.response.CardResponse;
import br.com.isac.domain.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/cartoes")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<CardResponse> createCard(@Valid @RequestBody CardRequest createCardRequest) {
        CardResponse response =  cardService.createCard(createCardRequest.toModel());
        return new ResponseEntity<>(response, CREATED);
    }

    @GetMapping(value = "/{numeroCartao}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable("numeroCartao") String numberCard) {
        return null;
    }

}
