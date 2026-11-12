package br.com.fiap.climasenior.repository;

import br.com.fiap.climasenior.model.Cidadao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CidadaoRepository extends JpaRepository<Cidadao, Long> {

    Optional<Cidadao> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    List<Cidadao> findByBairroIgnoreCaseContainingOrderByNomeCompletoAsc(String bairro);

    List<Cidadao> findByMobilidadeReduzidaTrueOrderByNomeCompletoAsc();

    long countByMobilidadeReduzidaTrue();
}