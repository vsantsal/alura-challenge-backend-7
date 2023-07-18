package br.com.alura.challengebackend.controller;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import br.com.alura.challengebackend.domain.repository.DepoimentosRepository;
import br.com.alura.challengebackend.service.DepoimentosService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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

    @DisplayName("Não deve permitir requisição com depoente vazio")
    @Test
    public void testCenario5() throws Exception {
        // Act
        this.mockMvc.perform(
                        post( "/depoimentos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"depoente\": \"\"," +
                                                "\"depoimento\": \"Meu depoimento\"," +
                                                " \"url_foto\": \"https://www.minhaimagem.com\"}" )
                )
                // Assert
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Não deve permitir requisição com depoente com mais de 120 caracteres")
    @Test
    public void testCenario6() throws Exception {
        // Act
        this.mockMvc.perform(
                        post( "/depoimentos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"depoente\": \"" +"a".repeat(121) + "\"," +
                                                "\"depoimento\": \""+ "b".repeat(500) +"\"," +
                                                " \"url_foto\": \"https://www.minhaimagem.com\"}" )
                )
                // Assert
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Não deve permitir requisição com depoimento com mais de 500 caracteres")
    @Test
    public void testCenario7() throws Exception {
        // Act
        this.mockMvc.perform(
                        post( "/depoimentos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"depoente\": \"" +"a".repeat(120) + "\"," +
                                                "\"depoimento\": \""+ "b".repeat(501) +"\"," +
                                                " \"url_foto\": \"https://www.minhaimagem.com\"}" )
                )
                // Assert
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Não deve permitir requisição com url foto vazio, se passado nos dados")
    @Test
    public void testCenario8() throws Exception {
        // Act
        this.mockMvc.perform(
                        post( "/depoimentos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"depoente\": \"" +"a".repeat(120) + "\"," +
                                                "\"depoimento\": \""+ "b".repeat(500) +"\"," +
                                                " \"url_foto\": \"\"}" )
                )
                // Assert
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Listagem de depoimentos para repositório com apenas um")
    @Test
    public void testCenario9() throws Exception {
        // Arrange
        when(repository.findAll(ArgumentMatchers.isA(Example.class))).thenReturn(
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
                .andExpect(jsonPath("$[0].depoente",
                        Matchers.is("Meu nome")))
                .andExpect(jsonPath("$[0].depoimento",
                        Matchers.is("Meu Depoimento")))
                .andExpect(jsonPath("$[0].url_foto",
                        Matchers.is("Minha foto")))
        ;
    }

    @DisplayName("Listagem de depoimentos para repositório com mais de um")
    @Test
    public void testCenario10() throws Exception {
        // Arrange
        when(repository.findAll(ArgumentMatchers.isA(Example.class))).thenReturn(
                List.of(
                        new Depoimento(
                                "Meu nome",
                                "Meu Depoimento",
                                "Minha foto"),
                        new Depoimento(
                                "Meu nome 2",
                                "Meu Depoimento 2",
                                "Minha foto 2")
                )
        );

        // Act
        this.mockMvc.perform(get("/depoimentos"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        Matchers.hasSize(2)))
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
        ;
    }

    @DisplayName("Listagem de depoimentos para repositório vazio")
    @Test
    public void testCenario11() throws Exception {
        // Arrange
        when(repository.findAll(ArgumentMatchers.isA(Example.class))).thenReturn(
                List.of()
        );

        // Act
        this.mockMvc.perform(get("/depoimentos"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        Matchers.hasSize(0)))
        ;
    }

}