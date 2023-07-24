package br.com.alura.challengebackend.dto;

import br.com.alura.challengebackend.domain.entity.Destino;

import java.math.BigDecimal;

public record DestinoDTO(
        Long id,
        String nome,
        BigDecimal preco,
        String urlFoto
) {
    public DestinoDTO(Destino destino) {
        this(destino.getId(), destino.getNome(), destino.getPreco(), destino.getUrlFoto());
    }
}
