package br.com.alura.challengebackend.domain.repository;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import br.com.alura.challengebackend.domain.entity.Destino;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class DestinosRepositoryTest {
    @Autowired
    private EntityManager manager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DestinosRepository repository;

    @DisplayName("Não é possível salvar destino sem nome")
    @Test
    public void testCenario1() {
        // Arrange / Act / Assert
        assertThatThrownBy(
                () -> repository.save(
                        new Destino(
                                null,
                                BigDecimal.ONE, "Minha Imagem")))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("Não é possível salvar destino com nome vazio")
    @Test
    public void testCenario2() {
        // Arrange / Act / Assert
        assertThatThrownBy(
                () -> repository.save(
                        new Destino(
                                "",
                                BigDecimal.ONE, "Minha Imagem")))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @DisplayName("Não é possível salvar destino com mais de 120 caracteres")
    @Test
    public void testCenario3() {
        // Arrange / Act / Assert
        assertThatThrownBy(
                () -> repository.save(
                        new Destino(
                                "a".repeat(121),
                                BigDecimal.ONE, "Minha Imagem")))
                .isInstanceOf(ConstraintViolationException.class);
    }

}