package br.com.ocupamais.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_historico_status")
public class HistoricoStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime data;
    private String responsavel;
    private String comentario;
    private String justificativa;

    public HistoricoStatus() {}

    public HistoricoStatus(Status status, String responsavel, String comentario, String justificativa) {
        this.status = status;
        this.data = LocalDateTime.now();
        this.responsavel = responsavel;
        this.comentario = comentario;
        this.justificativa = justificativa;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public String getComentario() {
        return comentario;
    }

    public String getJustificativa() {
        return justificativa;
    }
}