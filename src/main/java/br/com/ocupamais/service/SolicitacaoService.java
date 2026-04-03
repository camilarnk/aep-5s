package br.com.ocupamais.service;

import br.com.ocupamais.model.*;
import br.com.ocupamais.repository.SolicitacaoRepository;

import java.util.List;

public class SolicitacaoService {

    private final SolicitacaoRepository repository;

    public SolicitacaoService(SolicitacaoRepository repository) {
        this.repository = repository;
    }

    public Solicitacao criarSolicitacao(String descricao, String localizacao,
                                        Categoria categoria, Prioridade prioridade,
                                        boolean anonimo, String nomeSolicitante) {

        if(descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição obrigatória");
        }

        if(localizacao == null || localizacao.isBlank()) {
            throw new IllegalArgumentException("Localização obrigatória");
        }

        if (!anonimo && (nomeSolicitante == null || nomeSolicitante.isBlank())) {
            throw new IllegalArgumentException("Nome obrigatório para solicitações identificadas");
        }

        Solicitacao novaSolicitacao = new Solicitacao(
                descricao, localizacao, categoria, prioridade, anonimo, nomeSolicitante
        );

        repository.salvarSolicitacao(novaSolicitacao);
        return novaSolicitacao;
    }

    public List<Solicitacao> listarSolicitacoes() {
        return repository.listarSolicitacoes();
    }

    public Solicitacao buscarPorProtocolo(String protocolo) {
        return repository.buscarPorProtocolo(protocolo);
    }

    public void atualizarStatus(String protocolo, Status novoStatus,
                                String responsavel, String comentario) {

        if (responsavel == null || responsavel.isBlank()) {
            throw new IllegalArgumentException("Responsável obrigatório");
        }

        if (comentario == null || comentario.isBlank()) {
            throw new IllegalArgumentException("Comentário obrigatório");
        }

        Solicitacao solicitacao = repository.buscarPorProtocolo(protocolo);

        if(solicitacao == null) {
            throw new IllegalArgumentException("Solicitação não encontrada");
        }

        if(!fluxoValido(solicitacao.getStatus(), novoStatus)) {
            throw new IllegalArgumentException("Fluxo de status inválido. " +
                    "Siga: ABERTO - TRIAGEM - EM_EXECUCAO - RESOLVIDO - ENCERRADO");
        }

        solicitacao.setStatus(novoStatus);

        HistoricoStatus historico = new HistoricoStatus(novoStatus, responsavel, comentario);
        solicitacao.adicionarHistorico(historico);

        repository.salvarArquivo();
    }

    private boolean fluxoValido(Status atual, Status novo) {
        return switch(atual) {
            case ABERTO -> novo == Status.TRIAGEM;
            case TRIAGEM -> novo == Status.EM_EXECUCAO;
            case EM_EXECUCAO -> novo == Status.RESOLVIDO;
            case RESOLVIDO -> novo == Status.ENCERRADO;
            case ENCERRADO -> false;
        };
    }

}
