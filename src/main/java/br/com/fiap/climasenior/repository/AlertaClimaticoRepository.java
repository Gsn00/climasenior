package br.com.fiap.climasenior.repository;

import br.com.fiap.climasenior.model.AlertaClimatico;
import br.com.fiap.climasenior.model.enums.NivelAlerta;
import br.com.fiap.climasenior.model.enums.StatusAlerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaClimaticoRepository extends JpaRepository<AlertaClimatico, Long> {

    List<AlertaClimatico> findAllByOrderByDataHoraEmissaoDesc();

    List<AlertaClimatico> findByStatusOrderByDataHoraEmissaoDesc(StatusAlerta status);

    List<AlertaClimatico> findByNivelAndStatusOrderByDataHoraEmissaoDesc(NivelAlerta nivel, StatusAlerta status);

    List<AlertaClimatico> findByBairroAfetadoIgnoreCaseContainingOrderByDataHoraEmissaoDesc(String bairroAfetado);

    long countByStatus(StatusAlerta status);
}