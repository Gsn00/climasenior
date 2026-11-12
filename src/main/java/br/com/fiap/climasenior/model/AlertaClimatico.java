package br.com.fiap.climasenior.model;

import br.com.fiap.climasenior.model.enums.NivelAlerta;
import br.com.fiap.climasenior.model.enums.StatusAlerta;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "TB_CS_ALERTA_CLIMATICO")
public class AlertaClimatico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALERTA")
    private Long id;

    @NotBlank(message = "O título do alerta é obrigatório")
    @Size(max = 120, message = "O título deve ter no máximo 120 caracteres")
    @Column(name = "DS_TITULO", nullable = false, length = 120)
    private String titulo;

    @NotBlank(message = "A descrição/orientação do alerta é obrigatória")
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Column(name = "DS_ORIENTACAO", nullable = false, length = 500)
    private String descricao;

    @NotNull(message = "O nível do alerta é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "TP_NIVEL", nullable = false, length = 30)
    private NivelAlerta nivel;

    @NotNull(message = "O status do alerta é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "ST_ALERTA", nullable = false, length = 30)
    private StatusAlerta status = StatusAlerta.ATIVO;

    @NotBlank(message = "O bairro/região afetada é obrigatório")
    @Size(max = 80, message = "O bairro deve ter no máximo 80 caracteres")
    @Column(name = "NM_BAIRRO_AFETADO", nullable = false, length = 80)
    private String bairroAfetado;

    @Column(name = "DT_HORA_EMISSAO", nullable = false)
    private LocalDateTime dataHoraEmissao;

    @Column(name = "DT_HORA_RESOLUCAO")
    private LocalDateTime dataHoraResolucao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PONTO_REFERENCIA")
    private PontoApoio pontoApoioReferencia;

    public AlertaClimatico() {
        this.status = StatusAlerta.ATIVO;
        this.dataHoraEmissao = LocalDateTime.now();
    }

    public AlertaClimatico(Long id, String titulo, String descricao, NivelAlerta nivel, StatusAlerta status,
                           String bairroAfetado, LocalDateTime dataHoraEmissao, LocalDateTime dataHoraResolucao,
                           PontoApoio pontoApoioReferencia) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.nivel = nivel;
        this.status = status != null ? status : StatusAlerta.ATIVO;
        this.bairroAfetado = bairroAfetado;
        this.dataHoraEmissao = dataHoraEmissao != null ? dataHoraEmissao : LocalDateTime.now();
        this.dataHoraResolucao = dataHoraResolucao;
        this.pontoApoioReferencia = pontoApoioReferencia;
    }

    @PrePersist
    public void prePersist() {
        if (this.dataHoraEmissao == null) {
            this.dataHoraEmissao = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = StatusAlerta.ATIVO;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public NivelAlerta getNivel() {
        return nivel;
    }

    public void setNivel(NivelAlerta nivel) {
        this.nivel = nivel;
    }

    public StatusAlerta getStatus() {
        return status;
    }

    public void setStatus(StatusAlerta status) {
        this.status = status;
    }

    public String getBairroAfetado() {
        return bairroAfetado;
    }

    public void setBairroAfetado(String bairroAfetado) {
        this.bairroAfetado = bairroAfetado;
    }

    public LocalDateTime getDataHoraEmissao() {
        return dataHoraEmissao;
    }

    public void setDataHoraEmissao(LocalDateTime dataHoraEmissao) {
        this.dataHoraEmissao = dataHoraEmissao;
    }

    public LocalDateTime getDataHoraResolucao() {
        return dataHoraResolucao;
    }

    public void setDataHoraResolucao(LocalDateTime dataHoraResolucao) {
        this.dataHoraResolucao = dataHoraResolucao;
    }

    public PontoApoio getPontoApoioReferencia() {
        return pontoApoioReferencia;
    }

    public void setPontoApoioReferencia(PontoApoio pontoApoioReferencia) {
        this.pontoApoioReferencia = pontoApoioReferencia;
    }
}