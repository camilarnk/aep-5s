package br.com.ocupamais.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private LocalDateTime prazo;

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

        switch (prioridade) {
            case BAIXA -> prazo = dataCriacao.plusDays(14);
            case MEDIA -> prazo = dataCriacao.plusDays(7);
            case ALTA -> prazo = dataCriacao.plusDays(3);
        }
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

    public void adicionarHistorico(HistoricoStatus historicoStatus) {
        this.historico.add(historicoStatus);
    }

    @Override
    public String toString() {
        return "Protocolo: " + protocolo +
                " | Status: " + status +
                " | Categoria: " + categoria +
                " | Prioridade: " + prioridade;
    }

    public String detalharSolicitacao() {

        StringBuilder sb = new StringBuilder();

        sb.append("\n\n---- SOLICITAÇÃO ").append(protocolo).append(" - ").append(dataCriacao).append(" ----\n");

        sb.append("Descrição/Problema: ").append(descricao).append("\n");
        sb.append("Localização: ").append(localizacao).append("\n");
        sb.append("Categoria: ").append(categoria).append("\n");
        sb.append("Prioridade: ").append(prioridade).append("\n");
        sb.append("Status atual: ").append(status).append("\n");
        sb.append("Solicitante: ").append(nomeSolicitante).append("\n");
        sb.append("Prazo: ").append(prazo).append("\n");

        if (LocalDateTime.now().isAfter(prazo)) {
            sb.append("PRAZO ATRASADO!!\n");
        }

        sb.append("\n---- HISTÓRICO DE ATUALIZAÇÕES ----\n");

        if(historico.isEmpty()) {
            sb.append("Sem atualizações\n");
        } else {
            int i = 1;
            DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for(HistoricoStatus h : historico) {
                sb.append("\nAtualização ").append(i++);
                sb.append(" - ").append(h.getData().format(dataFormatada)).append("\n");
                sb.append("Responsável: ").append(h.getResponsavel()).append("\n");
                sb.append("Comentário: ").append(h.getComentario()).append("\n");
                sb.append("-----------------------------------\n");
            }
        }
        return sb.toString();
    }

}
