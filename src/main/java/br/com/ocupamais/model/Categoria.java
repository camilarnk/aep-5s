package br.com.ocupamais.model;

public enum Categoria {
    ILUMINACAO(1),
    BURACO(2),
    LIMPEZA(3),
    SAUDE(4),
    SEGURANCA(5),
    EDUCACAO(6),
    OUTROS(7);

    private int id;

    Categoria(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Categoria buscarPeloId(int id) {
        for(Categoria categoria : values()) {
            if(categoria.getId() == id) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("ID de Categoria inválido " + id);
    }

    public static void exibirOpcoes() {
        for (Categoria categoria : values()) {
            System.out.println(categoria.getId() + " - " + categoria);
        }
    }
}
