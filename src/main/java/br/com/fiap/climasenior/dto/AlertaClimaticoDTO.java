package br.com.fiap.climasenior.dto;

import br.com.fiap.climasenior.model.enums.NivelAlerta;
import br.com.fiap.climasenior.model.enums.StatusAlerta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class AlertaClimaticoDTO {

    private Long id;

    @NotBlank(message = "O título do alerta é obrigatório")
    @Size(max = 120, message = "O título deve ter no máximo 120 caracteres")
    private String titulo;

    @NotBlank(message = "A descrição/orientação do alerta é obrigatória")
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @NotNull(message = "O nível do alerta é obrigatório")
    private NivelAlerta nivel;

    private StatusAlerta status = StatusAlerta.ATIVO;

    @NotBlank(message = "O bairro/região afetada é obrigatório")
    @Size(max = 80, message = "O bairro deve ter no máximo 80 caracteres")
    private String bairroAfetado;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dataHoraEmissao;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dataHoraResolucao;

    private Long pontoApoioReferenciaId;

    private String pontoApoioReferenciaNome;

    public AlertaClimaticoDTO() {
    }

    public AlertaClimaticoDTO(Long id, String titulo, String descricao, NivelAlerta nivel, StatusAlerta status,
                              String bairroAfetado, LocalDateTime dataHoraEmissao, LocalDateTime dataHoraResolucao,
                              Long pontoApoioReferenciaId, String pontoApoioReferenciaNome) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.nivel = nivel;
        this.status = status != null ? status : StatusAlerta.ATIVO;
        this.bairroAfetado = bairroAfetado;
        this.dataHoraEmissao = dataHoraEmissao;
        this.dataHoraResolucao = dataHoraResolucao;
        this.pontoApoioReferenciaId = pontoApoioReferenciaId;
        this.pontoApoioReferenciaNome = pontoApoioReferenciaNome;
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

    public Long getPontoApoioReferenciaId() {
        return pontoApoioReferenciaId;
    }

    public void setPontoApoioReferenciaId(Long pontoApoioReferenciaId) {
        this.pontoApoioReferenciaId = pontoApoioReferenciaId;
    }

    public String getPontoApoioReferenciaNome() {
        return pontoApoioReferenciaNome;
    }

    public void setPontoApoioReferenciaNome(String pontoApoioReferenciaNome) {
        this.pontoApoioReferenciaNome = pontoApoioReferenciaNome;
    }
}