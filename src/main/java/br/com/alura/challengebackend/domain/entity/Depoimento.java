package br.com.alura.challengebackend.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, max = 120, message = "campo 'depoente' deve ter de 1 a 120 caracteres")
    private String depoente;

    @Column(nullable = false)
    @Setter
    @Size(min = 1, max = 500, message = "campo 'depoimento' deve ter de 1 a 500 caracteres")
    private String depoimento;

    @Column(name = "url_foto", nullable = true)
    @Setter
    @Size(min = 1, max = 255, message = "campo 'urlFoto' deve ter de 1 a 255 caracteres")
    private String urlFoto;

    @Deprecated
    public Depoimento(){}

    public Depoimento(String depoente, String depoimento, String urlFoto) {
        this.depoente = depoente;
        this.depoimento = depoimento;
        this.urlFoto = urlFoto;
    }
}
