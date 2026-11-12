package br.com.fiap.climasenior.model.enums;

public enum NivelAlerta {

    MODERADO("Moderado - Atenção"),
    ALTO("Alto - Risco Térmico"),
    CRITICO("Crítico - Emergência Climática");

    private final String descricao;

    NivelAlerta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}