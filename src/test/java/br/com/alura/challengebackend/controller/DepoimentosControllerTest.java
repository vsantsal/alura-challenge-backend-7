package br.com.alura.challengebackend.controller;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import br.com.alura.challengebackend.domain.repository.DepoimentosRepository;
import br.com.alura.challengebackend.service.DepoimentosService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(status().isCreated());
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
}