package br.com.alura.challengebackend.domain.repository;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.contains;

@ActiveProfiles("test")
@DataJpaTest
class DepoimentosRepositoryTest {

    @Autowired
    private EntityManager manager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DepoimentosRepository repository;


    @DisplayName("Se não houver depoimentos retorna lista vazia")
    @Test
    public void testCenario1(){
        // Arrange

        // Act
        List<Depoimento> depoimentos = repository.encontrarTresDepoimentosAleatoriamente();

        // Assert
        assertThat(depoimentos.size(), equalTo(0));
    }

    @DisplayName("Se possui só um depoimento retorna lista com ele")
    @Test
    public void testCenario2(){
        // Arrange
        Depoimento depoimentoUnico = new Depoimento("Fulano", "Meu Depoimento", null);
        repository.save(depoimentoUnico);
        // Act
        List<Depoimento> depoimentos = repository.encontrarTresDepoimentosAleatoriamente();

        // Assert
        assertThat(depoimentos.size(), equalTo(1));
        assertThat(depoimentos, contains(depoimentoUnico));

    }

    @DisplayName("Se possui 4 depoimentos retorna só 3")
    @Test
    public void testCenario3(){
        // Arrange
        repository.save(new Depoimento(
                "Usuário 1",
                "Meu Depoimento 1", null));
        repository.save(new Depoimento(
                "Usuário 2",
                "Meu Depoimento 2", "Minha Imagem 1"));
        repository.save(new Depoimento(
                "Usuário 3",
                "Meu Depoimento 3", "Minha Imagem 2"));
        repository.save(new Depoimento(
                "Usuário 4",
                "Meu Depoimento 4", null));

        // Act
        List<Depoimento> depoimentos = repository.encontrarTresDepoimentosAleatoriamente();

        // Assert
        assertThat(depoimentos.size(), equalTo(3));

    }

}