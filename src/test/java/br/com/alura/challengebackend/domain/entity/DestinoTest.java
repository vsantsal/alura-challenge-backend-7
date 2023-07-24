package br.com.alura.challengebackend.domain.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DestinoTest {

    private static Destino destino = new Destino("Destino", BigDecimal.ONE, "Foto");


    @DisplayName("Não permite atualização de preço com valor nulo")
    @Test
    public void testCenario1(){
        // Arrange
        BigDecimal valorNovo = null;

        // Act / Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> destino.setPreco(valorNovo)
        );
    }

    @DisplayName("Não permite atualização de preço com valor negativo")
    @Test
    public void testCenario2(){
        // Arrange
        BigDecimal valorNovo = new BigDecimal("-0.01");

        // Act / Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> destino.setPreco(valorNovo)
        );
    }

    @DisplayName("Não permite atualização de preço com valor com mais de duas casas decimais")
    @Test
    public void testCenario3(){
        // Arrange
        BigDecimal valorNovo = new BigDecimal("0.123");

        // Act / Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> destino.setPreco(valorNovo)
        );
    }

}