package br.com.alura.challengebackend.controller;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import br.com.alura.challengebackend.domain.repository.DepoimentosRepository;
import br.com.alura.challengebackend.service.DepoimentosService;
import jakarta.persistence.EntityNotFoundException;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class DepoimentosControllerTest {

    private final String ENDPOINT = "/depoimentos";

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
                post( ENDPOINT)
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
                        post( ENDPOINT)
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
                        post( ENDPOINT)
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
                        post( ENDPOINT)
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
                        post( ENDPOINT)
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
                        post( ENDPOINT)
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
                        post( ENDPOINT)
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
                        post( ENDPOINT)
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
        this.mockMvc.perform(get(ENDPOINT))
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
        this.mockMvc.perform(get(ENDPOINT))
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
        this.mockMvc.perform(get(ENDPOINT))
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        Matchers.hasSize(0)))
        ;
    }

    @DisplayName("Detalhe de depoimento com id válido")
    @Test
    public void testCenario12() throws Exception {
        // Arrange
        when(repository.getReferenceById(1L)).thenReturn(
                new Depoimento(
                        "meu nome",
                        "meu depoimento",
                        "minha foto"
                )
        );

        // Act
        this.mockMvc.perform(get(ENDPOINT + "/1"))
            // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.depoente",
                        Matchers.is("meu nome")))
                .andExpect(jsonPath("$.depoimento",
                        Matchers.is("meu depoimento")))
                .andExpect(jsonPath("$.url_foto",
                        Matchers.is("minha foto")))
        ;
    }

    @DisplayName("Detalhe de depoimento com id inválido")
    @Test
    public void testCenario13() throws Exception {
        //   Arrange
        when(repository.getReferenceById(2L)).thenThrow(
                EntityNotFoundException.class
        );

        // Act
        this.mockMvc.perform(get(ENDPOINT + "/2"))

                // Assert
                .andExpect(status().isNotFound());
    }

    @DisplayName("Exclusão de depoimento retorna status 204")
    @Test
    public void testCenario14() throws Exception {
        // Act
        this.mockMvc.perform(delete(ENDPOINT + "/1"))

                // Assert
                .andExpect(status().isNoContent());
    }

    @DisplayName("Deve atualizar com sucesso depoimento e url de foto")
    @Test
    public void testCenario15() throws Exception {
        // Arrange
        when(repository.getReferenceById(1L)).thenReturn(
                new Depoimento(
                        "Meu nome",
                        "Meu depoimento",
                        "https://www.minhaimagem.com"
                )
        );

        // Act
        this.mockMvc.perform(
                        put( ENDPOINT + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"depoente\": \"Meu nome\", " +
                                                "\"depoimento\": \"Meu depoimento 2\"," +
                                                " \"url_foto\": \"https://www.minhaimagem2.com\"}" )
                )
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.depoente",
                        Matchers.is("Meu nome")))
                .andExpect(jsonPath("$.depoimento",
                        Matchers.is("Meu depoimento 2")))
                .andExpect(jsonPath("$.url_foto",
                        Matchers.is("https://www.minhaimagem2.com")))
        ;
    }

    @DisplayName("Deve não deve atualizar nome de depoente")
    @Test
    public void testCenario16() throws Exception {
        // Arrange
        when(repository.getReferenceById(1L)).thenReturn(
                new Depoimento(
                        "Meu nome",
                        "Meu depoimento",
                        "https://www.minhaimagem.com"
                )
        );

        // Act
        this.mockMvc.perform(
                        put( ENDPOINT + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                    "{\"depoente\": \"Meu nome 2\", " +
                                    "\"depoimento\": \"Meu depoimento 2\"," +
                                    " \"url_foto\": \"https://www.minhaimagem2.com\"}" )
                )
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.depoente",
                        Matchers.is("Meu nome")))
                .andExpect(jsonPath("$.depoimento",
                        Matchers.is("Meu depoimento 2")))
                .andExpect(jsonPath("$.url_foto",
                        Matchers.is("https://www.minhaimagem2.com")))
        ;
    }

    @DisplayName("Não pode atualizar dados para id inexistente")
    @Test
    public void testCenario17() throws Exception {
        // Arrange
        when(repository.getReferenceById(1L)).thenThrow(
                EntityNotFoundException.class
        );

        // Act
        this.mockMvc.perform(
                        put( ENDPOINT + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                    "{\"depoente\": \"Meu nome 2\", " +
                                    "\"depoimento\": \"Meu depoimento 2\"," +
                                    " \"url_foto\": \"https://www.minhaimagem2.com\"}" )
                )
                // Assert
                .andExpect(status().isNotFound())
        ;
    }

}