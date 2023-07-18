package br.com.alura.challengebackend.controller;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import br.com.alura.challengebackend.domain.repository.DepoimentosRepository;
import br.com.alura.challengebackend.service.DepoimentosService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DepoimentosControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private DepoimentosService service;

    @MockBean
    private DepoimentosRepository repository;

    @DisplayName("Deve cadastrar com sucesso se dados informados validos")
    @Test
    public void testCenario1() throws Exception {
        // Arrange
        when(repository.save(any(Depoimento.class))).thenReturn(
                new Depoimento(
                        "Meu nome",
                        "Meu depoimento",
                        "https://www.minhaimagem.com"
                )
        );

        // Act
        this.mockMvc.perform(
                post( "/depoimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"depoente\": \"Meu nome\", " +
                                        "\"depoimento\": \"Meu depoimento\"," +
                                        " \"url_foto\": \"https://www.minhaimagem.com\"}" )
        )
                // Assert
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString("depoimentos/")));
    }

    @DisplayName("Não deve permitir requisição sem informar depoente")
    @Test
    public void testCenario2() throws Exception {
        // Act
        this.mockMvc.perform(
                        post( "/depoimentos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"depoimento\": \"Meu depoimento\"," +
                                                " \"url_foto\": \"https://www.minhaimagem.com\"}" )
                )
                // Assert
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Não deve permitir requisição sem informar depoimento")
    @Test
    public void testCenario3() throws Exception {
        // Act
        this.mockMvc.perform(
                        post( "/depoimentos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"depoente\": \"Meu nome\"," +
                                                " \"url_foto\": \"https://www.minhaimagem.com\"}" )
                )
                // Assert
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Deve cadastrar com sucesso se requisicao nao informa url foto")
    @Test
    public void testCenario4() throws Exception {
        // Arrange
        when(repository.save(any(Depoimento.class))).thenReturn(
                new Depoimento(
                        "Meu nome",
                        "Meu depoimento",
                        null
                )
        );

        // Act
        this.mockMvc.perform(
                        post( "/depoimentos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"depoente\": \"Meu nome\", " +
                                                "\"depoimento\": \"Meu depoimento\"}" )
                )
                // Assert
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString("depoimentos/")));

    }

    @Disabled("mockando ainda argumento de findall")
    @DisplayName("Listagem de depoimentos para repositório com apenas um")
    @Test
    public void testCenario5() throws Exception {
        // Arrange
        when(repository.findAll(Example.of(new Depoimento()))).thenReturn(
                List.of(
                        new Depoimento(
                                "Meu nome",
                                "Meu Depoimento",
                                "Minha foto")
                )
        );

        // Act
        this.mockMvc.perform(get("/depoimentos"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        Matchers.hasSize(1)))
        ;
    }

}