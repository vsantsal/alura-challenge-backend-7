package br.com.alura.challengebackend.controller;

import br.com.alura.challengebackend.dto.CadastroDeDepoimentoDTO;
import br.com.alura.challengebackend.dto.DepoimentoDTO;
import br.com.alura.challengebackend.service.DepoimentosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/depoimentos")
public class DepoimentosController {

    @Autowired
    private DepoimentosService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(
            @RequestBody @Valid CadastroDeDepoimentoDTO dados,
            UriComponentsBuilder uriComponentsBuilder
    ){
        DepoimentoDTO dadosSalvos = service.salvar(dados);
        var uri = uriComponentsBuilder.path("/depoimentos/{id}").buildAndExpand(dadosSalvos.id()).toUri();
        return ResponseEntity.created(uri).body(dadosSalvos);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(
            @PathVariable Long id
    ){
        DepoimentoDTO dados = service.detalhar(id);
        return ResponseEntity.ok(dados);

    }

}
