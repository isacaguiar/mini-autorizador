package br.com.isac.adapter.controller;

import br.com.isac.adapter.controller.request.CardRequest;
import br.com.isac.adapter.controller.response.CardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/cartoes")
public interface CardController {

    @PostMapping
    ResponseEntity<CardResponse> createCard(@RequestBody CardRequest createCardRequest);

    @GetMapping(value = "/{numeroCartao}")
    ResponseEntity<BigDecimal> getBalance(@PathVariable String numberCard);

}
