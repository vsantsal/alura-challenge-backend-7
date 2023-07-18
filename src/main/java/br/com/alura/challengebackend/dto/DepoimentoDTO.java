package br.com.alura.challengebackend.dto;

import br.com.alura.challengebackend.domain.entity.Depoimento;

public record DepoimentoDTO(
        Long id,
        String depoente,
        String depoimento,

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
