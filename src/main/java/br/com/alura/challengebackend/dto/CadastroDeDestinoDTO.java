package br.com.alura.challengebackend.dto;

import br.com.alura.challengebackend.domain.entity.Destino;

import java.math.BigDecimal;

public record CadastroDeDestinoDTO(
        String nome,

        BigDecimal preco,

        String urlFoto
) {
    public Destino toModel() {
        return new Destino(nome(), preco(), urlFoto());
    }
}
