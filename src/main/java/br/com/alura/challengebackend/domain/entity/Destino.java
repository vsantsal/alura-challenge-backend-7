package br.com.alura.challengebackend.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cd_destinos")
public class Destino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Long precoEmCentavos;

    @Transient
    private BigDecimal preco;

    private String urlFoto;

    @Deprecated
    public Destino(){}

    public Destino(String nome, BigDecimal preco, String urlFoto) {
        this.nome = nome;
        this.preco = preco;
        this.urlFoto = urlFoto;
        this.precoEmCentavos = this.preco.multiply(
                new BigDecimal("100"))
                .toBigInteger().longValue();
    }
}
