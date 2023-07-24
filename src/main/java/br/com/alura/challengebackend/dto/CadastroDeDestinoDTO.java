package br.com.alura.challengebackend.dto;

import br.com.alura.challengebackend.domain.entity.Destino;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CadastroDeDestinoDTO(
        @NotBlank
                @Size(max = 120)
        String nome,

        BigDecimal preco,

        String urlFoto
) {
    public Destino toModel() {
        return new Destino(nome(), preco(), urlFoto());
    }
}
