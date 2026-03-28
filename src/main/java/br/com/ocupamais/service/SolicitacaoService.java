package br.com.ocupamais.service;

import br.com.ocupamais.model.Categoria;
import br.com.ocupamais.model.Prioridade;
import br.com.ocupamais.model.Solicitacao;
import br.com.ocupamais.repository.SolicitacaoRepository;

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

}
