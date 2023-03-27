package br.com.isac.adapter.persistence;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "number", length = 16)
    private String number;

    @Column(name = "balance")
    private BigDecimal balance;
}
