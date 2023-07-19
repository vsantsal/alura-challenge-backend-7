package br.com.alura.challengebackend.domain.repository;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @DisplayName("Não é possível salvar depoimento sem depoente")
    @Test
    public void testCenario4() {
        // Arrange / Act / Assert
        assertThatThrownBy(
                () -> repository.save(
                        new Depoimento(
                        null,
                        "Meu Depoimento 1", "Minha Imagem")))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("Não é possível salvar depoimento sem depoimento")
    @Test
    public void testCenario5() {
        // Arrange / Act / Assert
        assertThatThrownBy(
                () -> repository.save(
                        new Depoimento(
                                "Meu nome",
                                null, "Minha Imagem")))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("Não é possível salvar depoimento com depoente com mais de 120 caracteres")
    @Test
    public void testCenario6() {
        // Arrange / Act / Assert
        assertThatThrownBy(
                () -> repository.save(
                        new Depoimento(
                                "a".repeat(121),
                                "b".repeat(500), "Minha Imagem")))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @DisplayName("Não é possível salvar depoimento com depoimento com mais de 500 caracteres")
    @Test
    public void testCenario7() {
        // Arrange / Act / Assert
        assertThatThrownBy(
                () -> repository.save(
                        new Depoimento(
                                "a".repeat(120),
                                "b".repeat(501), "Minha Imagem")))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @DisplayName("Não é possível salvar depoimento com nome de depoente vazio")
    @Test
    public void testCenario8() {
        // Arrange / Act / Assert
        assertThatThrownBy(
                () -> repository.save(
                        new Depoimento(
                                "",
                                "b".repeat(500), "Minha Imagem")))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @DisplayName("Não é possível salvar depoimento com depoimento vazio")
    @Test
    public void testCenario9() {
        // Arrange / Act / Assert
        assertThatThrownBy(
                () -> repository.save(
                        new Depoimento(
                                "a".repeat(120),
                                "", "Minha Imagem")))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @DisplayName("Não é possível salvar url de foto com mais de 255 caracteres")
    @Test
    public void testCenario10() {
        // Arrange / Act / Assert
        assertThatThrownBy(
                () -> repository.save(
                        new Depoimento(
                                "a".repeat(120),
                                "b".repeat(500), "c".repeat(256))))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @DisplayName("Não é possível salvar url de foto vazia")
    @Test
    public void testCenario11() {
        // Arrange / Act / Assert
        assertThatThrownBy(
                () -> repository.save(
                        new Depoimento(
                                "a".repeat(120),
                                "b".repeat(500), "")))
                .isInstanceOf(ConstraintViolationException.class);
    }

}