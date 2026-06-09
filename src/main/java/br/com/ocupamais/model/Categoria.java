package br.com.ocupamais.model;

public enum Categoria {
    ILUMINACAO("Iluminação"),
    BURACO("Buraco"),
    LIMPEZA("Limpeza"),
    SAUDE("Saúde"),
    SEGURANCA("Segurança"),
    EDUCACAO("Educação"),
    OUTROS("Outros");

    private final String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}