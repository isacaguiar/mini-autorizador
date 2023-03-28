package br.com.isac.adapter.persistence;

import com.sun.istack.NotNull;
import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card")
public class CardEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @NotNull
  @Column(name = "number", length = 16)
  private String number;

  @NotNull
  @Column(name = "password")
  private String password;

  @NotNull
  @Column(name = "balance")
  private BigDecimal balance;

}
