package br.com.alura.challengebackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Depoimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String depoente;

    @Column(nullable = false)
    private String depoimento;

    @Column(name = "url_foto")
    private String urlFoto;

    @Deprecated
    public Depoimento(){}

    public Depoimento(String depoente, String depoimento, String urlFoto) {
        this.depoente = depoente;
        this.depoimento = depoimento;
        this.urlFoto = urlFoto;
    }
}
