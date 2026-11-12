package br.com.fiap.climasenior.repository;

import br.com.fiap.climasenior.model.TelemetriaClimatica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelemetriaClimaticaRepository extends JpaRepository<TelemetriaClimatica, Long> {

    List<TelemetriaClimatica> findAllByOrderByDataHoraRegistroDesc();

    List<TelemetriaClimatica> findByPontoApoioIdOrderByDataHoraRegistroDesc(Long pontoApoioId);

    Optional<TelemetriaClimatica> findTopByPontoApoioIdOrderByDataHoraRegistroDesc(Long pontoApoioId);

    List<TelemetriaClimatica> findByTemperaturaGreaterThanEqualOrderByDataHoraRegistroDesc(Double temperatura);
}