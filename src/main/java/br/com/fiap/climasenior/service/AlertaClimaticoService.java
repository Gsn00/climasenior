package br.com.fiap.climasenior.service;

import br.com.fiap.climasenior.dto.AlertaClimaticoDTO;
import br.com.fiap.climasenior.exception.ResourceNotFoundException;
import br.com.fiap.climasenior.model.AlertaClimatico;
import br.com.fiap.climasenior.model.PontoApoio;
import br.com.fiap.climasenior.model.enums.StatusAlerta;
import br.com.fiap.climasenior.repository.AlertaClimaticoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertaClimaticoService {

    private final AlertaClimaticoRepository alertaRepository;
    private final PontoApoioService pontoApoioService;

    public AlertaClimaticoService(AlertaClimaticoRepository alertaRepository,
                                  PontoApoioService pontoApoioService) {
        this.alertaRepository = alertaRepository;
        this.pontoApoioService = pontoApoioService;
    }

    public List<AlertaClimaticoDTO> listarTodos() {
        return alertaRepository.findAllByOrderByDataHoraEmissaoDesc()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<AlertaClimaticoDTO> listarAtivos() {
        return alertaRepository.findByStatusOrderByDataHoraEmissaoDesc(StatusAlerta.ATIVO)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<AlertaClimaticoDTO> buscarPorBairro(String bairro) {
        if (bairro == null || bairro.isBlank()) {
            return listarTodos();
        }
        return alertaRepository.findByBairroAfetadoIgnoreCaseContainingOrderByDataHoraEmissaoDesc(bairro)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public AlertaClimaticoDTO buscarPorId(Long id) {
        AlertaClimatico alerta = buscarEntidadePorId(id);
        return toDTO(alerta);
    }

    public AlertaClimatico buscarEntidadePorId(Long id) {
        return alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta Climático", id));
    }

    public AlertaClimaticoDTO salvar(AlertaClimaticoDTO dto) {
        AlertaClimatico alerta = new AlertaClimatico();
        alerta.setTitulo(dto.getTitulo());
        alerta.setDescricao(dto.getDescricao());
        alerta.setNivel(dto.getNivel());
        alerta.setStatus(dto.getStatus() != null ? dto.getStatus() : StatusAlerta.ATIVO);
        alerta.setBairroAfetado(dto.getBairroAfetado());
        alerta.setDataHoraEmissao(dto.getDataHoraEmissao() != null ? dto.getDataHoraEmissao() : LocalDateTime.now());

        if (dto.getPontoApoioReferenciaId() != null) {
            PontoApoio ponto = pontoApoioService.buscarEntidadePorId(dto.getPontoApoioReferenciaId());
            alerta.setPontoApoioReferencia(ponto);
        }

        AlertaClimatico salvo = alertaRepository.save(alerta);
        return toDTO(salvo);
    }

    public AlertaClimaticoDTO atualizar(Long id, AlertaClimaticoDTO dto) {
        AlertaClimatico alerta = buscarEntidadePorId(id);
        alerta.setTitulo(dto.getTitulo());
        alerta.setDescricao(dto.getDescricao());
        alerta.setNivel(dto.getNivel());
        alerta.setBairroAfetado(dto.getBairroAfetado());

        if (dto.getStatus() != null) {
            alerta.setStatus(dto.getStatus());
            if (dto.getStatus() == StatusAlerta.RESOLVIDO && alerta.getDataHoraResolucao() == null) {
                alerta.setDataHoraResolucao(LocalDateTime.now());
            } else if (dto.getStatus() != StatusAlerta.RESOLVIDO) {
                alerta.setDataHoraResolucao(null);
            }
        }

        if (dto.getPontoApoioReferenciaId() != null) {
            PontoApoio ponto = pontoApoioService.buscarEntidadePorId(dto.getPontoApoioReferenciaId());
            alerta.setPontoApoioReferencia(ponto);
        } else {
            alerta.setPontoApoioReferencia(null);
        }

        AlertaClimatico atualizado = alertaRepository.save(alerta);
        return toDTO(atualizado);
    }

    public void resolverAlerta(Long id) {
        AlertaClimatico alerta = buscarEntidadePorId(id);
        alerta.setStatus(StatusAlerta.RESOLVIDO);
        alerta.setDataHoraResolucao(LocalDateTime.now());
        alertaRepository.save(alerta);
    }

    public void excluir(Long id) {
        AlertaClimatico alerta = buscarEntidadePorId(id);
        alertaRepository.delete(alerta);
    }

    public long contarAtivos() {
        return alertaRepository.countByStatus(StatusAlerta.ATIVO);
    }

    public AlertaClimaticoDTO toDTO(AlertaClimatico entity) {
        Long pontoId = entity.getPontoApoioReferencia() != null ? entity.getPontoApoioReferencia().getId() : null;
        String pontoNome = entity.getPontoApoioReferencia() != null ? entity.getPontoApoioReferencia().getNome() : null;

        return new AlertaClimaticoDTO(
                entity.getId(),
                entity.getTitulo(),
                entity.getDescricao(),
                entity.getNivel(),
                entity.getStatus(),
                entity.getBairroAfetado(),
                entity.getDataHoraEmissao(),
                entity.getDataHoraResolucao(),
                pontoId,
                pontoNome
        );
    }
}