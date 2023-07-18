package br.com.alura.challengebackend.domain.repository;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepoimentosRepository extends JpaRepository<Depoimento, Long> {
}
