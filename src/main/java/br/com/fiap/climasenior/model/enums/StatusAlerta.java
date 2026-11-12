package br.com.fiap.climasenior.model.enums;

public enum StatusAlerta {

    ATIVO("Ativo"),
    EM_ATENDIMENTO("Em Atendimento"),
    RESOLVIDO("Resolvido"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusAlerta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}