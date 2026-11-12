package br.com.fiap.climasenior.repository;

import br.com.fiap.climasenior.model.PontoApoio;
import br.com.fiap.climasenior.model.enums.TipoPontoApoio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PontoApoioRepository extends JpaRepository<PontoApoio, Long> {

    List<PontoApoio> findByAtivoTrueOrderByNomeAsc();

    List<PontoApoio> findByBairroIgnoreCaseContainingAndAtivoTrue(String bairro);

    List<PontoApoio> findByTipoAndAtivoTrue(TipoPontoApoio tipo);

    long countByAtivoTrue();
}