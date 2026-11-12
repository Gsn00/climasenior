package br.com.fiap.climasenior.dto;

import br.com.fiap.climasenior.model.enums.TipoPontoApoio;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PontoApoioDTO {

    private Long id;

    @NotBlank(message = "O nome do ponto de apoio é obrigatório")
    @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
    private String nome;

    @NotNull(message = "O tipo do ponto de apoio é obrigatório")
    private TipoPontoApoio tipo;

    @NotBlank(message = "O endereço é obrigatório")
    @Size(max = 200, message = "O endereço deve ter no máximo 200 caracteres")
    private String endereco;

    @NotBlank(message = "O bairro/região é obrigatório")
    @Size(max = 80, message = "O bairro deve ter no máximo 80 caracteres")
    private String bairro;

    @NotNull(message = "A capacidade máxima é obrigatória")
    @Min(value = 1, message = "A capacidade mínima deve ser de 1 pessoa")
    private Integer capacidadeMaxima;

    private Boolean ativo = true;

    public PontoApoioDTO() {
    }

    public PontoApoioDTO(Long id, String nome, TipoPontoApoio tipo, String endereco, String bairro, Integer capacidadeMaxima, Boolean ativo) {
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
}