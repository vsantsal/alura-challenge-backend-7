package br.com.alura.challengebackend.dto;

import br.com.alura.challengebackend.domain.entity.Destino;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CadastroDeDestinoDTO(
        @NotBlank
                @Size(max = 120)
        String nome,

        @NotNull
                @Positive
                @Digits(integer = 15, fraction = 2)
        BigDecimal preco,

        @JsonProperty("url_foto")
        @Size(min = 1, max = 255)
        String urlFoto
) {
    public Destino toModel() {
        return new Destino(nome(), preco(), urlFoto());
    }
}
