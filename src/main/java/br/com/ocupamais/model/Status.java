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
}
