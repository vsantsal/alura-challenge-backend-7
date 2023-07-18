package br.com.alura.challengebackend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cd_depoimentos")
@Getter
public class Depoimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String depoente;

    @Column(nullable = false)
    @Setter
    private String depoimento;

    @Column(name = "url_foto", nullable = true)
    @Setter
    private String urlFoto;

    @Deprecated
    public Depoimento(){}

    public Depoimento(String depoente, String depoimento, String urlFoto) {
        this.depoente = depoente;
        this.depoimento = depoimento;
        this.urlFoto = urlFoto;
    }
}
