package br.com.isac.domain.model;

import lombok.*;
import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Card {

  private Long id;

  private String number;

  private String password;

  private BigDecimal balance;

}
