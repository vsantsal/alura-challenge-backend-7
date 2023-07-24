package br.com.alura.challengebackend.controller;


import br.com.alura.challengebackend.domain.entity.Destino;
import br.com.alura.challengebackend.domain.repository.DestinosRepository;
import br.com.alura.challengebackend.dto.DestinoDTO;
import br.com.alura.challengebackend.service.DestinosService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class DestinosControllerTest {

    private final String ENDPOINT = "/destinos";

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private DestinosService service;

    @MockBean
    private DestinosRepository repository;

    @DisplayName("Deve cadastrar com sucesso se dados informados validos")
    @Test
    public void testCenario1() throws Exception {
        // Arrange
        when(repository.save(any(Destino.class))).thenReturn(
                new Destino(
                        "Destino",
                        new BigDecimal("1234.56"),
                        "https://www.imagemdestino.com"
                )
        );

        // Act
        this.mockMvc.perform(
                        post( ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"nome\": \"Destino\", " +
                                                "\"preco\": 1234.56," +
                                                " \"url_foto\": \"https://www.imagemdestino.com\"}" )
                )
                // Assert
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString(ENDPOINT)));
    }

    @DisplayName("Não deve permitir requisição sem informar nome de destino")
    @Test
    public void testCenario2() throws Exception {
        // Act
        this.mockMvc.perform(
                        post( ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"preco\": 1234.56," +
                                                " \"url_foto\": \"https://www.minhaimagem.com\"}" )
                )
                // Assert
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Não deve permitir requisição com nome de destino com mais de 120 caracteres")
    @Test
    public void testCenario3() throws Exception {
        // Act
        this.mockMvc.perform(
                        post( ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"nome\": \"" +"a".repeat(121) + "\"," +
                                                "\"preco\": 1234.56," +
                                                " \"url_foto\": \"https://www.minhaimagem.com\"}" )
                )
                // Assert
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Não deve permitir requisição com nome de destino vazio")
    @Test
    public void testCenario4() throws Exception {
        // Act
        this.mockMvc.perform(
                        post( ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"nome\": \"\"," +
                                                "\"preco\": 1234.56," +
                                                " \"url_foto\": \"https://www.minhaimagem.com\"}" )
                )
                // Assert
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Não deve permitir requisição sem informar preço")
    @Test
    public void testCenario5() throws Exception {
        // Act
        this.mockMvc.perform(
                        post( ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"nome\": \"abc\"," +
                                                " \"url_foto\": \"https://www.minhaimagem.com\"}" )
                )
                // Assert
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Não deve permitir cadastro de destino com preço negativo")
    @Test
    public void testCenario6() throws Exception {
        // Act
        this.mockMvc.perform(
                        post( ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"nome\": \"Destino\", " +
                                                "\"preco\": -0.01," +
                                                " \"url_foto\": \"https://www.imagemdestino.com\"}" )
                )
                // Assert
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Não deve permitir cadastro de destino com preço zero")
    @Test
    public void testCenario7() throws Exception {
        // Act
        this.mockMvc.perform(
                        post( ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"nome\": \"Destino\", " +
                                                "\"preco\": 0," +
                                                " \"url_foto\": \"https://www.imagemdestino.com\"}" )
                )
                // Assert
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Não deve permitir cadastro de destino com mais de duas casas decimais")
    @Test
    public void testCenario8() throws Exception {
        // Act
        this.mockMvc.perform(
                        post( ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"nome\": \"Destino\", " +
                                                "\"preco\": 1234.567," +
                                                " \"url_foto\": \"https://www.imagemdestino.com\"}" )
                )
                // Assert
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Deve cadastrar com sucesso se não for informad url de foto")
    @Test
    public void testCenario9() throws Exception {
        // Arrange
        when(repository.save(any(Destino.class))).thenReturn(
                new Destino(
                        "Destino",
                        new BigDecimal("1234.56"),
                        null
                )
        );

        // Act
        this.mockMvc.perform(
                        post( ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"nome\": \"Destino\", " +
                                                "\"preco\": 1234.56}")
                )
                // Assert
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString(ENDPOINT)));
    }

    @DisplayName("Não deve permitir cadastro de destino com url de foto passada e vazia")
    @Test
    public void testCenario10() throws Exception {
        // Act
        this.mockMvc.perform(
                        post( ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"nome\": \"Destino\", " +
                                                "\"preco\": 1234.56," +
                                                " \"url_foto\": \"\"}" )
                )
                // Assert
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Não deve permitir cadastro de destino com url de foto passada com mais de 255 caracteres")
    @Test
    public void testCenario11() throws Exception {
        // Act
        this.mockMvc.perform(
                        post( ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"nome\": \"Destino\", " +
                                                "\"preco\": 1234.56," +
                                                " \"url_foto\": \"" + "a".repeat(256) + "\"}" )
                )
                // Assert
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Listagem de destinos para repositório com apenas um")
    @Test
    public void testCenario12() throws Exception {
        // Arrange
        when(repository.findAll(ArgumentMatchers.isA(Example.class))).thenReturn(
                List.of(
                        new Destino(
                                "Meu destino",
                                new BigDecimal("1234.56"),
                                "Minha foto")
                )
        );

        // Act
        this.mockMvc.perform(get(ENDPOINT))
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].nome",
                        Matchers.is("Meu destino")))
                .andExpect(jsonPath("$[0].preco",
                        Matchers.is(1234.56)))
                .andExpect(jsonPath("$[0].url_foto",
                        Matchers.is("Minha foto")))
        ;
    }


}