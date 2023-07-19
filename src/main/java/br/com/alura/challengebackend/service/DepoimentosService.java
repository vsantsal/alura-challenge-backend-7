package br.com.alura.challengebackend.service;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import br.com.alura.challengebackend.domain.repository.DepoimentosRepository;
import br.com.alura.challengebackend.dto.CadastroDeDepoimentoDTO;
import br.com.alura.challengebackend.dto.DepoimentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepoimentosService {

    @Autowired
    private DepoimentosRepository repository;

    public DepoimentoDTO salvar(CadastroDeDepoimentoDTO dados) {
        Depoimento depoimentoASalvar = dados.toModel();
        Depoimento depoimentoSalvo = repository.save(depoimentoASalvar);
        return new DepoimentoDTO(depoimentoSalvo);
    }

    public DepoimentoDTO detalhar(Long id) {
        Depoimento depoimento = repository.getReferenceById(id);
        return new DepoimentoDTO(depoimento);
    }

    public List<DepoimentoDTO> listar(DepoimentoDTO paramPesquisa) {
        Depoimento depoimento = paramPesquisa.toModel();
        Example<Depoimento> exemplo = Example.of(depoimento);
        List<Depoimento> depoimentos = repository.findAll(exemplo);
        return depoimentos.stream().map(
                DepoimentoDTO::new
        ).toList();
    }

    public void excluir(Long id) {
        this.repository.deleteById(id);
    }

    public DepoimentoDTO atualizar(DepoimentoDTO dto) {
        Depoimento depoimentoAAtualizar = repository.getReferenceById(dto.id());
        depoimentoAAtualizar.setDepoimento(dto.depoimento());
        depoimentoAAtualizar.setUrlFoto(dto.urlFoto());
        repository.save(depoimentoAAtualizar);
        return new DepoimentoDTO(depoimentoAAtualizar);
    }

    public List<DepoimentoDTO> listarTresDepoimentosAleatorios() {
        List<Depoimento> depoimentos = repository.encontrarTresDepoimentosAleatoriamente();
        return depoimentos.stream().map(
                DepoimentoDTO::new
        ).toList();
    }
}
