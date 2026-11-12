package br.com.fiap.climasenior.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class CidadaoDTO {

    private Long id;

    @NotBlank(message = "O nome completo é obrigatório")
    @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
    private String nomeCompleto;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF inválido (use 11 dígitos ou 000.000.000-00)")
    private String cpf;

    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve estar no passado")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @NotBlank(message = "O telefone de contato é obrigatório")
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    private String telefone;

    @NotBlank(message = "O bairro de residência é obrigatório")
    @Size(max = 80, message = "O bairro deve ter no máximo 80 caracteres")
    private String bairro;

    @Size(max = 250, message = "As observações devem ter no máximo 250 caracteres")
    private String observacaoSaude;

    private Boolean mobilidadeReduzida = false;

    public CidadaoDTO() {
    }

    public CidadaoDTO(Long id, String nomeCompleto, String cpf, LocalDate dataNascimento, String telefone, String bairro, String observacaoSaude, Boolean mobilidadeReduzida) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.bairro = bairro;
        this.observacaoSaude = observacaoSaude;
        this.mobilidadeReduzida = mobilidadeReduzida != null ? mobilidadeReduzida : false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getObservacaoSaude() {
        return observacaoSaude;
    }

    public void setObservacaoSaude(String observacaoSaude) {
        this.observacaoSaude = observacaoSaude;
    }

    public Boolean getMobilidadeReduzida() {
        return mobilidadeReduzida;
    }

    public void setMobilidadeReduzida(Boolean mobilidadeReduzida) {
        this.mobilidadeReduzida = mobilidadeReduzida;
    }
}