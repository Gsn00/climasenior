package br.com.fiap.climasenior.model;

import br.com.fiap.climasenior.model.enums.TipoPontoApoio;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_CS_PONTO_APOIO")
public class PontoApoio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PONTO")
    private Long id;

    @NotBlank(message = "O nome do ponto de apoio é obrigatório")
    @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
    @Column(name = "NM_PONTO", nullable = false, length = 120)
    private String nome;

    @NotNull(message = "O tipo do ponto de apoio é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "TP_PONTO", nullable = false, length = 40)
    private TipoPontoApoio tipo;

    @NotBlank(message = "O endereço é obrigatório")
    @Size(max = 200, message = "O endereço deve ter no máximo 200 caracteres")
    @Column(name = "DS_ENDERECO", nullable = false, length = 200)
    private String endereco;

    @NotBlank(message = "O bairro/região é obrigatório")
    @Size(max = 80, message = "O bairro deve ter no máximo 80 caracteres")
    @Column(name = "NM_BAIRRO", nullable = false, length = 80)
    private String bairro;

    @NotNull(message = "A capacidade máxima é obrigatória")
    @Min(value = 1, message = "A capacidade mínima deve ser de 1 pessoa")
    @Column(name = "NR_CAPACIDADE_MAXIMA", nullable = false)
    private Integer capacidadeMaxima;

    @Column(name = "ST_ATIVO", nullable = false)
    private Boolean ativo = true;

    @OneToMany(mappedBy = "pontoApoio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TelemetriaClimatica> leituras = new ArrayList<>();

    public PontoApoio() {
    }

    public PontoApoio(Long id, String nome, TipoPontoApoio tipo, String endereco, String bairro, Integer capacidadeMaxima, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.endereco = endereco;
        this.bairro = bairro;
        this.capacidadeMaxima = capacidadeMaxima;
        this.ativo = ativo != null ? ativo : true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoPontoApoio getTipo() {
        return tipo;
    }

    public void setTipo(TipoPontoApoio tipo) {
        this.tipo = tipo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Integer getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(Integer capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<TelemetriaClimatica> getLeituras() {
        return leituras;
    }

    public void setLeituras(List<TelemetriaClimatica> leituras) {
        this.leituras = leituras;
    }
}