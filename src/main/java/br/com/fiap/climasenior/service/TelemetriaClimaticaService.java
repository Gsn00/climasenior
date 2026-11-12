package br.com.fiap.climasenior.service;

import br.com.fiap.climasenior.dto.TelemetriaClimaticaDTO;
import br.com.fiap.climasenior.exception.ResourceNotFoundException;
import br.com.fiap.climasenior.model.AlertaClimatico;
import br.com.fiap.climasenior.model.PontoApoio;
import br.com.fiap.climasenior.model.TelemetriaClimatica;
import br.com.fiap.climasenior.model.enums.NivelAlerta;
import br.com.fiap.climasenior.model.enums.StatusAlerta;
import br.com.fiap.climasenior.repository.AlertaClimaticoRepository;
import br.com.fiap.climasenior.repository.TelemetriaClimaticaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TelemetriaClimaticaService {

    private final TelemetriaClimaticaRepository telemetriaRepository;
    private final PontoApoioService pontoApoioService;
    private final AlertaClimaticoRepository alertaRepository;

    public TelemetriaClimaticaService(TelemetriaClimaticaRepository telemetriaRepository,
                                      PontoApoioService pontoApoioService,
                                      AlertaClimaticoRepository alertaRepository) {
        this.telemetriaRepository = telemetriaRepository;
        this.pontoApoioService = pontoApoioService;
        this.alertaRepository = alertaRepository;
    }

    public List<TelemetriaClimaticaDTO> listarTodas() {
        return telemetriaRepository.findAllByOrderByDataHoraRegistroDesc()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<TelemetriaClimaticaDTO> listarPorPontoApoio(Long pontoApoioId) {
        return telemetriaRepository.findByPontoApoioIdOrderByDataHoraRegistroDesc(pontoApoioId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public TelemetriaClimaticaDTO buscarPorId(Long id) {
        TelemetriaClimatica telemetria = telemetriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de Telemetria", id));
        return toDTO(telemetria);
    }

    public TelemetriaClimaticaDTO registrarLeitura(TelemetriaClimaticaDTO dto) {
        PontoApoio ponto = pontoApoioService.buscarEntidadePorId(dto.getPontoApoioId());

        Double sensacao = dto.getSensacaoTermica() != null
                ? dto.getSensacaoTermica()
                : calcularSensacaoTermica(dto.getTemperatura(), dto.getUmidade());

        TelemetriaClimatica telemetria = new TelemetriaClimatica();
        telemetria.setTemperatura(dto.getTemperatura());
        telemetria.setUmidade(dto.getUmidade());
        telemetria.setSensacaoTermica(sensacao);
        telemetria.setDataHoraRegistro(dto.getDataHoraRegistro() != null ? dto.getDataHoraRegistro() : LocalDateTime.now());
        telemetria.setPontoApoio(ponto);

        TelemetriaClimatica salva = telemetriaRepository.save(telemetria);

        verificarEGerarAlertaAutomatico(salva);

        return toDTO(salva);
    }

    public void excluir(Long id) {
        TelemetriaClimatica telemetria = telemetriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de Telemetria", id));
        telemetriaRepository.delete(telemetria);
    }

    private Double calcularSensacaoTermica(Double temperatura, Double umidade) {
        double ajusteUmidade = (umidade - 50.0) * 0.05;
        double resultado = temperatura + ajusteUmidade;
        return Math.round(resultado * 10.0) / 10.0;
    }

    private void verificarEGerarAlertaAutomatico(TelemetriaClimatica leitura) {
        double temp = leitura.getTemperatura();
        double umidade = leitura.getUmidade();

        if (temp >= 35.0 || umidade <= 20.0) {
            AlertaClimatico alerta = new AlertaClimatico();
            alerta.setBairroAfetado(leitura.getPontoApoio().getBairro());
            alerta.setPontoApoioReferencia(leitura.getPontoApoio());
            alerta.setStatus(StatusAlerta.ATIVO);
            alerta.setDataHoraEmissao(LocalDateTime.now());

            if (temp >= 38.0 || umidade <= 15.0) {
                alerta.setNivel(NivelAlerta.CRITICO);
                alerta.setTitulo("Emergência Climática - Calor Extremo / Baixa Umidade");
                alerta.setDescricao(String.format(
                        "Leitura crítica registrada (%.1f°C e %.0f%% de umidade). Risco severo de desidratação e hipertermia para idosos. Recomenda-se deslocamento imediato ao ponto de apoio: %s.",
                        temp, umidade, leitura.getPontoApoio().getNome()
                ));
            } else {
                alerta.setNivel(NivelAlerta.ALTO);
                alerta.setTitulo("Alerta de Risco Térmico Elevado");
                alerta.setDescricao(String.format(
                        "Temperatura elevada (%.1f°C) ou umidade baixa (%.0f%%) detectada. Reforçar hidratação e evitar exposição ao sol. Ponto de apoio próximo: %s.",
                        temp, umidade, leitura.getPontoApoio().getNome()
                ));
            }

            alertaRepository.save(alerta);
        }
    }

    public TelemetriaClimaticaDTO toDTO(TelemetriaClimatica entity) {
        return new TelemetriaClimaticaDTO(
                entity.getId(),
                entity.getTemperatura(),
                entity.getUmidade(),
                entity.getSensacaoTermica(),
                entity.getDataHoraRegistro(),
                entity.getPontoApoio().getId(),
                entity.getPontoApoio().getNome()
        );
    }
}