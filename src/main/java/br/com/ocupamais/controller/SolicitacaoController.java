package br.com.ocupamais.controller;

import br.com.ocupamais.model.Categoria;
import br.com.ocupamais.model.Prioridade;
import br.com.ocupamais.model.Solicitacao;
import br.com.ocupamais.model.Status;
import br.com.ocupamais.service.SolicitacaoService;

import java.util.List;

public class SolicitacaoController {

    private final SolicitacaoService service;

    public SolicitacaoController(SolicitacaoService service) {
        this.service = service;
    }

    public Solicitacao criarSolicitacao(String descricao, String localizacao,
                                 Categoria categoria, Prioridade prioridade,
                                 boolean anonimo, String nomeSolicitante) {

        return service.criarSolicitacao(descricao, localizacao, categoria, prioridade,
                anonimo, nomeSolicitante);
    }

    public List<Solicitacao> listarSolicitacoes() {
        return service.listarSolicitacoes();
    }

    public List<Solicitacao> listarSolicitacoesAbertas() {
        return service.listarSolicitacoesAbertas();
    }

    public List<Solicitacao> filtrarPorCategoria(Categoria categoria) {
        return service.filtrarPorCategoria(categoria);
    }

    public List<Solicitacao> filtrarPorPrioridade(Prioridade prioridade) {
        return service.filtrarPorPrioridade(prioridade);
    }

    public List<Solicitacao> filtrarPorLocalizacao(String localizacao) {
        return service.filtrarPorLocalizacao(localizacao);
    }

    public Solicitacao buscarPorProtocolo(String protocolo) {
        return service.buscarPorProtocolo(protocolo);
    }

    public void atualizarStatus(String protocolo, Status status,
                                String responsavel, String comentario) {

        service.atualizarStatus(protocolo, status, responsavel, comentario);
    }

}
