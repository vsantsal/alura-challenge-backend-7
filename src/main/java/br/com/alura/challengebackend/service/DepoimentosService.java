package br.com.alura.challengebackend.service;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import br.com.alura.challengebackend.domain.repository.DepoimentosRepository;
import br.com.alura.challengebackend.dto.CadastroDeDepoimentoDTO;
import br.com.alura.challengebackend.dto.DepoimentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepoimentosService {

    @Autowired
    private DepoimentosRepository repository;

    public DepoimentoDTO salvar(CadastroDeDepoimentoDTO dados) {
        Depoimento depoimentoASalvar = dados.toModel();
        Depoimento depoimentoSalvo = repository.save(depoimentoASalvar);
        return new DepoimentoDTO(depoimentoSalvo);
    }
}
