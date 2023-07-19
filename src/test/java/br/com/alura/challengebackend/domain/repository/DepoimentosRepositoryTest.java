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

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(depoimentos.size(), 0);
    }

}