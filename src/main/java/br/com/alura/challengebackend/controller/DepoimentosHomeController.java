package br.com.alura.challengebackend.controller;

import br.com.alura.challengebackend.dto.DepoimentoDTO;
import br.com.alura.challengebackend.service.DepoimentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/depoimentos-home")
public class DepoimentosHomeController {

    @Autowired
    private DepoimentosService service;

    @GetMapping
    public ResponseEntity tresAleatorios() {
        List<DepoimentoDTO> depoimentosHome = service.listarTresDepoimentosAleatorios();
        return ResponseEntity.ok(depoimentosHome);
    }

}
