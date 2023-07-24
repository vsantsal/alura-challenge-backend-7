package br.com.alura.challengebackend.controller;

import br.com.alura.challengebackend.dto.CadastroDeDestinoDTO;
import br.com.alura.challengebackend.dto.DestinoDTO;
import br.com.alura.challengebackend.service.DestinosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/destinos")
public class DestinosController {

    @Autowired
    private DestinosService service;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(
            @RequestBody @Valid CadastroDeDestinoDTO dados,
            UriComponentsBuilder uriComponentsBuilder
    ){
        DestinoDTO dadosSalvos = service.salvar(dados);
        var uri = uriComponentsBuilder.path("/destinos/{id}").buildAndExpand(dadosSalvos.id()).toUri();
        return ResponseEntity.created(uri).body(dadosSalvos);
    }

    @GetMapping
    public ResponseEntity listar(
            @RequestParam(value = "nome", required = false) String nome
    ){
        DestinoDTO paramPesquisa = new DestinoDTO(null, nome, null, null);
        List<DestinoDTO> depoimentosEncontrados = service.listar(paramPesquisa);
        return ResponseEntity.ok(depoimentosEncontrados);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(
            @PathVariable Long id
    ){
        DestinoDTO dados = service.detalhar(id);
        return ResponseEntity.ok(dados);

    }

}
