package br.com.alura.challengebackend.controller;

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
    public void testCenario1(){
        // Arrange
        when(repository.save(any(Depoimento.class))).thenReturn(
                new Depoimento(
                        1L,
                        "https://www.minhaimagem.com",
                        "Meu depoimento",
                        "Meu nome"
                )
        );

        // Act
        this.mockMvc.perform(
                post( "/depoimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                "{\"depoente\": \"Meu nome\", " +
                                        "\"depoimento\": \"Meu depoimento\"," +
                                        " \"url_foto\": \"https://www.minhaimagem.com}" )
        )
        // Assert
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString("depoimentos/1")));

    }
}