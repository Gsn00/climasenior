package br.com.fiap.climasenior.model.enums;

public enum TipoPontoApoio {

    ABRIGO_CLIMATIZADO("Abrigo Climatizado"),
    PONTO_HIDRATACAO("Ponto de Hidratação"),
    POSTO_SAUDE("Posto de Saúde / Atendimento"),
    CENTRO_CONVIVENCIA("Centro de Convivência do Idoso");

    private final String descricao;

    TipoPontoApoio(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}