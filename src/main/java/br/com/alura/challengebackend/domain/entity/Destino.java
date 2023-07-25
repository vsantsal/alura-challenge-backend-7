package br.com.alura.challengebackend.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cd_destinos")
public class Destino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Column(nullable = false)
    @Size(min = 1, max = 120, message = "campo 'nome' deve possuir de 1 a 120 caracteres")
    private String nome;

    @Column(nullable = false)
    private Long precoEmCentavos;

    @Getter
    @Transient
    @NotNull
    @Positive
    private BigDecimal preco;

    @Getter
    @Setter
    @Size(min = 1, max = 255)
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

    public void setPreco(BigDecimal preco) {
        valida(preco);
        this.preco = preco;
        this.precoEmCentavos = this.preco.multiply(new BigDecimal("100")).longValue();
    }

    private static void valida(BigDecimal preco) {
        if (preco == null ||
                preco.compareTo(BigDecimal.ZERO) < 0
                || preco.scale()  > 2) {
            throw new IllegalArgumentException("preco deve ser positivo");
        }
    }
}
