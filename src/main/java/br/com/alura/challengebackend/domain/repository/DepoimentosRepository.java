package br.com.alura.challengebackend.domain.repository;

import br.com.alura.challengebackend.domain.entity.Depoimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepoimentosRepository extends JpaRepository<Depoimento, Long> {
    @Query("""
           SELECT d FROM Depoimento d
           ORDER BY random()
           LIMIT 3
           """
    )
    List<Depoimento> encontrarTresDepoimentosAleatoriamente();
}
