package br.com.alura.challengebackend.dto;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public record CadastroDeDepoimentoDTO(
        @NotBlank(message = "campo 'depoente' obrigatório")
        String depoente,
        String depoimento,
        @JsonAlias(value = "url_foto")
        String urlFoto
) {
    public Depoimento toModel() {
        return new Depoimento(depoente(), depoimento(), urlFoto());
    }
}
