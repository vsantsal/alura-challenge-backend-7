package br.com.alura.challengebackend.dto;

import br.com.alura.challengebackend.domain.entity.Destino;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record DestinoDTO(
        Long id,
        String nome,
        BigDecimal preco,
        @JsonProperty("url_foto")
        String urlFoto
) {
    public DestinoDTO(Destino destino) {
        this(destino.getId(), destino.getNome(), destino.getPreco(), destino.getUrlFoto());
    }

    public Destino toModel() {
        return new Destino(nome(), preco(), urlFoto());
    }
}
