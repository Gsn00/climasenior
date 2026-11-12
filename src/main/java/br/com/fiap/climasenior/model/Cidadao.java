package br.com.fiap.climasenior.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "TB_CS_CIDADAO")
public class Cidadao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CIDADAO")
    private Long id;

    @NotBlank(message = "O nome completo é obrigatório")
    @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
    @Column(name = "NM_COMPLETO", nullable = false, length = 120)
    private String nomeCompleto;

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF inválido (use 11 dígitos ou 000.000.000-00)")
    @Column(name = "NR_CPF", nullable = false, unique = true, length = 14)
    private String cpf;

    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve estar no passado")
    @Column(name = "DT_NASCIMENTO", nullable = false)
    private LocalDate dataNascimento;

    @NotBlank(message = "O telefone de contato é obrigatório")
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    @Column(name = "NR_TELEFONE", nullable = false, length = 20)
    private String telefone;

    @NotBlank(message = "O bairro de residência é obrigatório")
    @Size(max = 80, message = "O bairro deve ter no máximo 80 caracteres")
    @Column(name = "NM_BAIRRO", nullable = false, length = 80)
    private String bairro;

    @Size(max = 250, message = "As observações devem ter no máximo 250 caracteres")
    @Column(name = "DS_OBSERVACAO_SAUDE", length = 250)
    private String observacaoSaude;

    @Column(name = "ST_MOBILIDADE_REDUZIDA", nullable = false)
    private Boolean mobilidadeReduzida = false;

    public Cidadao() {
    }

    public Cidadao(Long id, String nomeCompleto, String cpf, LocalDate dataNascimento, String telefone, String bairro, String observacaoSaude, Boolean mobilidadeReduzida) {
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