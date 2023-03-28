package br.com.isac.domain.controller.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    private String cardNumber;
    private String cardPassword;
    private BigDecimal value;

}
