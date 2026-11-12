package br.com.fiap.climasenior.service;

import br.com.fiap.climasenior.dto.PontoApoioDTO;
import br.com.fiap.climasenior.exception.ResourceNotFoundException;
import br.com.fiap.climasenior.model.PontoApoio;
import br.com.fiap.climasenior.repository.PontoApoioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PontoApoioService {

    private final PontoApoioRepository pontoApoioRepository;

    public PontoApoioService(PontoApoioRepository pontoApoioRepository) {
        this.pontoApoioRepository = pontoApoioRepository;
    }

    public List<PontoApoioDTO> listarTodos() {
        return pontoApoioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<PontoApoioDTO> listarAtivos() {
        return pontoApoioRepository.findByAtivoTrueOrderByNomeAsc()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<PontoApoioDTO> buscarPorBairro(String bairro) {
        if (bairro == null || bairro.isBlank()) {
            return listarAtivos();
        }
        return pontoApoioRepository.findByBairroIgnoreCaseContainingAndAtivoTrue(bairro)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public PontoApoioDTO buscarPorId(Long id) {
        PontoApoio ponto = buscarEntidadePorId(id);
        return toDTO(ponto);
    }

    public PontoApoio buscarEntidadePorId(Long id) {
        return pontoApoioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de Apoio", id));
    }

    public PontoApoioDTO salvar(PontoApoioDTO dto) {
        PontoApoio ponto = new PontoApoio();
        ponto.setNome(dto.getNome());
        ponto.setTipo(dto.getTipo());
        ponto.setEndereco(dto.getEndereco());
        ponto.setBairro(dto.getBairro());
        ponto.setCapacidadeMaxima(dto.getCapacidadeMaxima());
        ponto.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);

        PontoApoio salvo = pontoApoioRepository.save(ponto);
        return toDTO(salvo);
    }

    public PontoApoioDTO atualizar(Long id, PontoApoioDTO dto) {
        PontoApoio ponto = buscarEntidadePorId(id);
        ponto.setNome(dto.getNome());
        ponto.setTipo(dto.getTipo());
        ponto.setEndereco(dto.getEndereco());
        ponto.setBairro(dto.getBairro());
        ponto.setCapacidadeMaxima(dto.getCapacidadeMaxima());
        ponto.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : false);

        PontoApoio atualizado = pontoApoioRepository.save(ponto);
        return toDTO(atualizado);
    }

    public void excluir(Long id) {
        PontoApoio ponto = buscarEntidadePorId(id);
        pontoApoioRepository.delete(ponto);
    }

    public long contarAtivos() {
        return pontoApoioRepository.countByAtivoTrue();
    }

    public PontoApoioDTO toDTO(PontoApoio entity) {
        return new PontoApoioDTO(
                entity.getId(),
                entity.getNome(),
                entity.getTipo(),
                entity.getEndereco(),
                entity.getBairro(),
                entity.getCapacidadeMaxima(),
                entity.getAtivo()
        );
    }
}