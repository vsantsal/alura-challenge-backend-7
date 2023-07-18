package br.com.alura.challengebackend.dto;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroDeDepoimentoDTO(
        @NotBlank(message = "campo 'depoente' obrigatório")
                @Size(min = 1, max = 120)
        String depoente,
        @NotBlank(message = "campo 'depoimento' obrigatório")
                @Size(min = 1, max = 500)
        String depoimento,
        @JsonAlias(value = "url_foto")
        String urlFoto
) {
    public Depoimento toModel() {
        return new Depoimento(depoente(), depoimento(), urlFoto());
    }
}
