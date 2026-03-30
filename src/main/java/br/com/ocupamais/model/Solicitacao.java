package br.com.ocupamais.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Solicitacao implements Serializable {

    // controle de versao da serializaçao de arquivo
    private static final long serialVersionUID = 1L;

    private String protocolo;
    private String descricao;
    private String localizacao;
    private LocalDateTime dataCriacao;

    private Categoria categoria;
    private Prioridade prioridade;
    private Status status;
    private List<HistoricoStatus> historico;

    private boolean anonimo;
    private String nomeSolicitante;

    public Solicitacao(String descricao, String localizacao,
                       Categoria categoria, Prioridade prioridade,
                        boolean anonimo, String nomeSolicitante) {
        this.protocolo = UUID.randomUUID().toString(); // cria id unico como o codigo do protocolo
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.dataCriacao = LocalDateTime.now();
        this.categoria = categoria;
        this.prioridade = prioridade;
        this.status = Status.ABERTO;
        this.historico = new ArrayList<>();
        this.anonimo = anonimo;
        this.nomeSolicitante = anonimo ? "Anonimo" : nomeSolicitante;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public Status getStatus() {
        return status;
    }

    public List<HistoricoStatus> getHistorico() {
        return historico;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // adicionar registro de mudança
    public void adicionarHistorico(HistoricoStatus historicoStatus) {
        this.historico.add(historicoStatus);
    }

    @Override
    public String toString() {
        return "Protocolo: " + protocolo +
                "\nDescrição: " + descricao +
                "\nLocalização: " + localizacao +
                "\nCategoria: " + categoria +
                "\nPrioridade: " + prioridade +
                "\nStatus: " + status +
                "\nData: " + dataCriacao +
                "\n---------------------------";
    }
}
