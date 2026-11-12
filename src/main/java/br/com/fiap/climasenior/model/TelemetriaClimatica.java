package br.com.fiap.climasenior.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "TB_CS_TELEMETRIA_CLIMATICA")
public class TelemetriaClimatica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TELEMETRIA")
    private Long id;

    @NotNull(message = "A temperatura é obrigatória")
    @DecimalMin(value = "-20.0", message = "Temperatura inválida")
    @DecimalMax(value = "60.0", message = "Temperatura inválida")
    @Column(name = "VL_TEMPERATURA", nullable = false)
    private Double temperatura;

    @NotNull(message = "A umidade relativa do ar é obrigatória")
    @DecimalMin(value = "0.0", message = "A umidade deve ser no mínimo 0%")
    @DecimalMax(value = "100.0", message = "A umidade deve ser no máximo 100%")
    @Column(name = "VL_UMIDADE", nullable = false)
    private Double umidade;

    @Column(name = "VL_SENSACAO_TERMICA")
    private Double sensacaoTermica;

    @NotNull(message = "A data e hora do registro são obrigatórias")
    @Column(name = "DT_HORA_REGISTRO", nullable = false)
    private LocalDateTime dataHoraRegistro;

    @NotNull(message = "O ponto de apoio vinculado é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PONTO", nullable = false)
    private PontoApoio pontoApoio;

    public TelemetriaClimatica() {
        this.dataHoraRegistro = LocalDateTime.now();
    }

    public TelemetriaClimatica(Long id, Double temperatura, Double umidade, Double sensacaoTermica, LocalDateTime dataHoraRegistro, PontoApoio pontoApoio) {
        this.id = id;
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.sensacaoTermica = sensacaoTermica;
        this.dataHoraRegistro = dataHoraRegistro != null ? dataHoraRegistro : LocalDateTime.now();
        this.pontoApoio = pontoApoio;
    }

    @PrePersist
    public void prePersist() {
        if (this.dataHoraRegistro == null) {
            this.dataHoraRegistro = LocalDateTime.now();
        }
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

    public PontoApoio getPontoApoio() {
        return pontoApoio;
    }

    public void setPontoApoio(PontoApoio pontoApoio) {
        this.pontoApoio = pontoApoio;
    }
}