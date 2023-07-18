package br.com.alura.challengebackend.dto;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DepoimentoDTO(
        Long id,
        String depoente,
        String depoimento,

        @JsonProperty("url_foto")
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
