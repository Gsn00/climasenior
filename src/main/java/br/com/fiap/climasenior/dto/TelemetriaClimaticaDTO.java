package br.com.fiap.climasenior.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class TelemetriaClimaticaDTO {

    private Long id;

    @NotNull(message = "A temperatura é obrigatória")
    @DecimalMin(value = "-20.0", message = "Temperatura inválida")
    @DecimalMax(value = "60.0", message = "Temperatura inválida")
    private Double temperatura;

    @NotNull(message = "A umidade relativa do ar é obrigatória")
    @DecimalMin(value = "0.0", message = "A umidade deve ser no mínimo 0%")
    @DecimalMax(value = "100.0", message = "A umidade deve ser no máximo 100%")
    private Double umidade;

    private Double sensacaoTermica;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dataHoraRegistro;

    @NotNull(message = "O ponto de apoio vinculado é obrigatório")
    private Long pontoApoioId;

    private String pontoApoioNome;

    public TelemetriaClimaticaDTO() {
    }

    public TelemetriaClimaticaDTO(Long id, Double temperatura, Double umidade, Double sensacaoTermica, LocalDateTime dataHoraRegistro, Long pontoApoioId, String pontoApoioNome) {
        this.id = id;
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.sensacaoTermica = sensacaoTermica;
        this.dataHoraRegistro = dataHoraRegistro;
        this.pontoApoioId = pontoApoioId;
        this.pontoApoioNome = pontoApoioNome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getUmidade() {
        return umidade;
    }

    public void setUmidade(Double umidade) {
        this.umidade = umidade;
    }

    public Double getSensacaoTermica() {
        return sensacaoTermica;
    }

    public void setSensacaoTermica(Double sensacaoTermica) {
        this.sensacaoTermica = sensacaoTermica;
    }

    public LocalDateTime getDataHoraRegistro() {
        return dataHoraRegistro;
    }

    public void setDataHoraRegistro(LocalDateTime dataHoraRegistro) {
        this.dataHoraRegistro = dataHoraRegistro;
    }

    public Long getPontoApoioId() {
        return pontoApoioId;
    }

    public void setPontoApoioId(Long pontoApoioId) {
        this.pontoApoioId = pontoApoioId;
    }

    public String getPontoApoioNome() {
        return pontoApoioNome;
    }

    public void setPontoApoioNome(String pontoApoioNome) {
        this.pontoApoioNome = pontoApoioNome;
    }
}