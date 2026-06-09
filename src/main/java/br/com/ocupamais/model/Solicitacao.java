package br.com.ocupamais.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_solicitacao")
public class Solicitacao {

    @Id
    @Column(unique = true, nullable = false)
    private String protocolo;

    private String descricao;
    private String localizacao;
    private LocalDateTime dataCriacao;
    private LocalDateTime prazo;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "solicitacao_protocolo")
    @OrderBy("data DESC")
    private List<HistoricoStatus> historico = new ArrayList<>();

    private boolean anonimo;
    private String nomeSolicitante;

    public Solicitacao() {}

    public Solicitacao(String descricao, String localizacao,
                       Categoria categoria, Prioridade prioridade,
                       boolean anonimo, String nomeSolicitante) {

        this.descricao = descricao;
        this.localizacao = localizacao;
        this.dataCriacao = LocalDateTime.now();
        this.categoria = categoria;
        this.prioridade = prioridade;
        this.status = Status.ABERTO;
        this.anonimo = anonimo;
        this.nomeSolicitante = anonimo ? "Anonimo" : nomeSolicitante;

        this.protocolo = UUID.randomUUID()
                .toString()
                .replace("-", "").substring(0, 8)
                .toUpperCase();

        switch (prioridade) {
            case BAIXA -> prazo = dataCriacao.plusDays(14);
            case MEDIA -> prazo = dataCriacao.plusDays(7);
            case ALTA -> prazo = dataCriacao.plusDays(3);
        }

        historico.add(
            new HistoricoStatus(
                    Status.ABERTO,
                    "Sistema",
                    "Solicitação criada",
                    null
            )
        );
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

    public LocalDateTime getPrazo() {
        return prazo;
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

    public boolean isAnonimo() {
        return anonimo;
    }

    public String getNomeSolicitante() {
        return nomeSolicitante;
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
}