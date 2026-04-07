package br.com.ocupamais.model;

public enum Prioridade {
    BAIXA(1),
    MEDIA(2),
    ALTA(3);

    private int id;

    Prioridade(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Prioridade buscarPeloId(int id) {
        for (Prioridade prioridade : values()) {
            if (prioridade.getId() == id) {
                return prioridade;
            }
        }
        throw new IllegalArgumentException("ID inválido");
    }

    public static void exibirOpcoes() {
        for (Prioridade prioridade : values()) {
            System.out.println(prioridade.getId() + " - " + prioridade);
        }
    }
}
