package br.com.alura.challengebackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Table(name = "cd_destinos")
public class Destino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private String nome;

    private Long precoEmCentavos;

    @Getter
    @Transient
    private BigDecimal preco;

    @Getter
    private String urlFoto;

    @Deprecated
    public Destino(){}

    public Destino(String nome, BigDecimal preco, String urlFoto) {
        this.nome = nome;
        this.preco = preco;
        this.urlFoto = urlFoto;
        if (this.preco != null) {
            this.precoEmCentavos = this.preco.multiply(
                            new BigDecimal("100"))
                    .toBigInteger().longValue();

        }
    }
}
