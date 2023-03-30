package br.com.isac.domain.vo;

import lombok.*;
import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Card {

  private Long id;

  private String number;

  private String password;

  private BigDecimal balance;

}
