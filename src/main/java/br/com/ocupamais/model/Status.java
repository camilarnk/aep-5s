package br.com.ocupamais.model;

public enum Status {
    ABERTO(1),
    TRIAGEM(2),
    EM_EXECUCAO(3),
    RESOLVIDO(4),
    ENCERRADO(5);

    private int id;

    Status(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Status buscarPeloId(int id) {
        for(Status status : values()) {
            if(status.getId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("ID de Status inválido: " + id);
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

    public String getDescricao() {
        return switch (this) {
            case ABERTO -> "Aberto";
            case TRIAGEM -> "Triagem";
            case EM_EXECUCAO -> "Em Execução";
            case RESOLVIDO -> "Resolvido";
            case ENCERRADO -> "Encerrado";
        };
    }
}