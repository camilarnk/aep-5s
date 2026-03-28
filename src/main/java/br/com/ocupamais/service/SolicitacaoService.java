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
                                        Categoria categoria, Prioridade prioridade) {

        if(descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição obrigatória");
        }

        if(localizacao == null || localizacao.isBlank()) {
            throw new IllegalArgumentException("Localização obrigatória");
        }

        Solicitacao novaSolicitacao = new Solicitacao(descricao, localizacao, categoria, prioridade);
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

        Solicitacao solicitacao = repository.buscarPorProtocolo(protocolo);

        if(solicitacao == null) {
            throw new IllegalArgumentException("Solicitação não encontrada");
        }

        solicitacao.setStatus(novoStatus);

        HistoricoStatus historico = new HistoricoStatus(novoStatus, responsavel, comentario);
        solicitacao.adicionarHistorico(historico);

        repository.salvarSolicitacao(solicitacao);
    }

}
