package br.com.ocupamais.model;

public enum Status {
    ABERTO("Aberto", 20),
    TRIAGEM("Triagem", 40),
    EM_EXECUCAO("Em Execução", 60),
    RESOLVIDO("Resolvido", 80),
    ENCERRADO("Encerrado", 100);

    private final String descricao;
    private final int progresso;

    Status(String descricao, int progresso) {
        this.descricao = descricao;
        this.progresso = progresso;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getProgresso() {
        return progresso;
    }

    public Status proximo() {
        return switch (this) {
            case ABERTO -> TRIAGEM;
            case TRIAGEM -> EM_EXECUCAO;
            case EM_EXECUCAO -> RESOLVIDO;
            case RESOLVIDO -> ENCERRADO;
            case ENCERRADO -> null;
        };
    }
}