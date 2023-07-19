package br.com.alura.challengebackend.controller;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import br.com.alura.challengebackend.domain.repository.DepoimentosRepository;
import br.com.alura.challengebackend.service.DepoimentosService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class DepoimentosHomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private DepoimentosService service;

    @MockBean
    private DepoimentosRepository repository;

    @DisplayName("Listagem de depoimentos para repositório com mais de um")
    @Test
    public void testCenario10() throws Exception {
        // Arrange
        when(repository.encontrarTresDepoimentosAleatoriamente()).thenReturn(
                List.of(
                        new Depoimento(
                                "Meu nome",
                                "Meu Depoimento",
                                "Minha foto"),
                        new Depoimento(
                                "Meu nome 2",
                                "Meu Depoimento 2",
                                "Minha foto 2"),
                        new Depoimento(
                                "Meu nome 3",
                                "Meu Depoimento 3",
                                "Minha foto 3")
                )
        );

        // Act
        this.mockMvc.perform(get("/depoimentos-home"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].depoente",
                        Matchers.is("Meu nome")))
                .andExpect(jsonPath("$[0].depoimento",
                        Matchers.is("Meu Depoimento")))
                .andExpect(jsonPath("$[0].url_foto",
                        Matchers.is("Minha foto")))
                .andExpect(jsonPath("$[1].depoente",
                        Matchers.is("Meu nome 2")))
                .andExpect(jsonPath("$[1].depoimento",
                        Matchers.is("Meu Depoimento 2")))
                .andExpect(jsonPath("$[1].url_foto",
                        Matchers.is("Minha foto 2")))
                .andExpect(jsonPath("$[2].depoente",
                        Matchers.is("Meu nome 3")))
                .andExpect(jsonPath("$[2].depoimento",
                        Matchers.is("Meu Depoimento 3")))
                .andExpect(jsonPath("$[2].url_foto",
                        Matchers.is("Minha foto 3")))
        ;
    }

    @DisplayName("Listagem de depoimentos para repositório vazio")
    @Test
    public void testCenario11() throws Exception {
        // Arrange
        when(repository.encontrarTresDepoimentosAleatoriamente()).thenReturn(
                List.of()
        );

        // Act
        this.mockMvc.perform(get("/depoimentos-home"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        Matchers.hasSize(0)))
        ;
    }

}