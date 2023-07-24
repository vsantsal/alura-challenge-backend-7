package br.com.alura.challengebackend.service;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import br.com.alura.challengebackend.domain.entity.Destino;
import br.com.alura.challengebackend.domain.repository.DestinosRepository;
import br.com.alura.challengebackend.dto.CadastroDeDestinoDTO;
import br.com.alura.challengebackend.dto.DepoimentoDTO;
import br.com.alura.challengebackend.dto.DestinoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DestinosService {

    @Autowired
    private DestinosRepository repository;

    public DestinoDTO salvar(CadastroDeDestinoDTO dados) {
        var destinoASalvar = dados.toModel();
        var destinoSalvo = repository.save(destinoASalvar);
        return new DestinoDTO(destinoSalvo);

    }
}
