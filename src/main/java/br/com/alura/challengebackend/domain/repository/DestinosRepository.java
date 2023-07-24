package br.com.alura.challengebackend.domain.repository;

import br.com.alura.challengebackend.domain.entity.Destino;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinosRepository extends JpaRepository<Destino, Long> {
}
