package br.com.alura.challengebackend.service;


import br.com.alura.challengebackend.domain.entity.Destino;
import br.com.alura.challengebackend.domain.repository.DestinosRepository;
import br.com.alura.challengebackend.dto.CadastroDeDestinoDTO;
import br.com.alura.challengebackend.dto.DestinoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinosService {

    @Autowired
    private DestinosRepository repository;

    public DestinoDTO salvar(CadastroDeDestinoDTO dados) {
        var destinoASalvar = dados.toModel();
        var destinoSalvo = repository.save(destinoASalvar);
        return new DestinoDTO(destinoSalvo);

    }

    public List<DestinoDTO> listar(DestinoDTO paramPesquisa) {
        Destino destino = paramPesquisa.toModel();
        Example<Destino> exemplo = Example.of(destino);
        List<Destino> destinos = repository.findAll(exemplo);
        return  destinos.stream().map(
                DestinoDTO::new
        ).toList();
    }

    public DestinoDTO detalhar(Long id) {
        var destino = repository.getReferenceById(id);
        return new DestinoDTO(destino);
    }

    public void excluir(Long id) {
        this.repository.deleteById(id);
    }

    public DestinoDTO atualizar(DestinoDTO dto) {
        Destino destinoAAtualizar = repository.getReferenceById(dto.id());
        destinoAAtualizar.setPreco(dto.preco());
        destinoAAtualizar.setUrlFoto(dto.urlFoto());
        repository.save(destinoAAtualizar);
        return new DestinoDTO(destinoAAtualizar);
    }
}
