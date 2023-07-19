package br.com.alura.challengebackend.dto;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonPropertyOrder({"id", "depoente", "depoimento", "url_foto"})
public record DepoimentoDTO(
        Long id,
        @NotBlank
                @Size(min = 1, max = 120)
        String depoente,
        @NotBlank
                @Size(min = 1, max = 500)
        String depoimento,

        @JsonProperty("url_foto")
                @Size(min = 1, max = 255)
        String urlFoto
) {
    public DepoimentoDTO(Depoimento depoimentoSalvo) {
        this(depoimentoSalvo.getId(), depoimentoSalvo.getDepoente(),
                depoimentoSalvo.getDepoimento(), depoimentoSalvo.getUrlFoto());
    }

    public Depoimento toModel() {
        return new Depoimento(depoente(), depoimento(), urlFoto());
    }
}
