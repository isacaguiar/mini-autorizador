package br.com.isac.domain.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Builder
@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {

    private String senha;
    private String numeroCartao;
}
