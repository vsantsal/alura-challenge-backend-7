package br.com.alura.challengebackend.dto;

import br.com.alura.challengebackend.domain.entity.Destino;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record DestinoDTO(
        Long id,
        @NotBlank
        @Size(max = 120)
        String nome,
        @NotNull(message = "campo obrigatório")
        @Positive(message = "preço informado deve ser positivo")
        @Digits(integer = 15, fraction = 2)
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
