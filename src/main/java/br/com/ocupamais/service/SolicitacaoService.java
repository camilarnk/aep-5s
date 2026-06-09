package br.com.ocupamais.service;

import br.com.ocupamais.model.*;
import br.com.ocupamais.repository.SolicitacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository repository;

    public SolicitacaoService(SolicitacaoRepository repository) {
        this.repository = repository;
    }

    public Solicitacao criarSolicitacao(String descricao, String endereco,
                                        String bairro, String referencia,
                                        Categoria categoria, Prioridade prioridade,
                                        boolean anonimo, String nomeSolicitante) {

        if(descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição obrigatória");
        }

        if(endereco == null || endereco.isBlank()) {
            throw new IllegalArgumentException("Endereço obrigatório");
        }

        if(bairro == null || bairro.isBlank()) {
            throw new IllegalArgumentException("Bairro obrigatório");
        }

        if (!anonimo && (nomeSolicitante == null || nomeSolicitante.isBlank())) {
            throw new IllegalArgumentException("Nome obrigatório para solicitações identificadas");
        }

        String localizacao = endereco + " - " + bairro;

        if (referencia != null && !referencia.isBlank()) {
            localizacao += " - " + referencia;
        }

        Solicitacao novaSolicitacao = new Solicitacao(
                descricao, localizacao, categoria, prioridade, anonimo, nomeSolicitante
        );

        repository.save(novaSolicitacao);
        return novaSolicitacao;
    }


    public List<Solicitacao> buscarComFiltros(String categoria,
                                              String prioridade,
                                              String localizacao) {
        return repository.findAll().stream()
                .filter(s ->
                        categoria == null
                                || categoria.isBlank()
                                || categoria.equals("TODAS")
                                || s.getCategoria().name().equals(categoria))

                .filter(s ->
                        prioridade == null
                                || prioridade.isBlank()
                                || prioridade.equals("TODAS")
                                || s.getPrioridade().name().equals(prioridade))

                .filter(s ->
                        localizacao == null
                                || localizacao.isBlank()
                                || s.getLocalizacao()
                                .toLowerCase()
                                .contains(localizacao.toLowerCase()))
                .sorted(Comparator.comparing(Solicitacao::getPrioridade).reversed())
                .toList();
    }

    public Solicitacao buscarPorProtocolo(String protocolo) {
        return repository.findById(protocolo).orElse(null);
    }

    public void atualizarStatus(String protocolo, Status novoStatus,
                                String responsavel, String comentario,
                                String justificativa) {

        if (responsavel == null || responsavel.isBlank()) {
            throw new IllegalArgumentException("Responsável obrigatório");
        }

        if (comentario == null || comentario.isBlank()) {
            throw new IllegalArgumentException("Comentário obrigatório");
        }

        Solicitacao solicitacao = repository.findById(protocolo).orElse(null);

        if(solicitacao == null) {
            throw new IllegalArgumentException("Solicitação não encontrada");
        }

        if (LocalDateTime.now().isAfter(solicitacao.getPrazo())) {
            if (justificativa == null || justificativa.isBlank()) {
                throw new IllegalArgumentException("Solicitação atrasada exige justificativa!");
            }
        }

        if(!fluxoValido(solicitacao.getStatus(), novoStatus)) {
            throw new IllegalArgumentException("Fluxo de status inválido. " +
                    "Siga: ABERTO - TRIAGEM - EM_EXECUCAO - RESOLVIDO - ENCERRADO");
        }

        solicitacao.setStatus(novoStatus);

        HistoricoStatus historico = new HistoricoStatus(novoStatus, responsavel, comentario, justificativa);
        solicitacao.adicionarHistorico(historico);

        repository.save(solicitacao);
    }

    private boolean fluxoValido(Status atual, Status novo) {
        return atual.proximo() == novo;
    }

}