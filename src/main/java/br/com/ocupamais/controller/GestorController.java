package br.com.ocupamais.controller;

import br.com.ocupamais.model.Solicitacao;
import br.com.ocupamais.model.Status;
import br.com.ocupamais.service.SolicitacaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/gestor")
public class GestorController {

    private final SolicitacaoService service;

    public GestorController(SolicitacaoService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String login() {
        return "gestor/login";
    }

    @GetMapping("/painel")
    public String painel() {
        return "gestor/painel";
    }

    @GetMapping("/solicitacoes")
    public String listarSolicitacoes(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String prioridade,
            @RequestParam(required = false) String localizacao,
            Model model) {

        List<Solicitacao> solicitacoes = service.buscarComFiltros(categoria, prioridade, localizacao);

        model.addAttribute("solicitacoes", solicitacoes);

        return "gestor/solicitacoes";
    }

    @GetMapping("/atualizar")
    public String atualizar(@RequestParam(required = false) String protocolo, Model model) {

        if (protocolo != null && !protocolo.isBlank()) {
            Solicitacao solicitacao = service.buscarPorProtocolo(protocolo);

            if (solicitacao != null) {
                carregarDadosSolicitacao(solicitacao, model);
            } else {
                model.addAttribute("erro",
                        "Nenhuma solicitação encontrada para o protocolo informado.");
            }
        }

        return "gestor/atualizar";
    }

    @GetMapping("/detalhes")
    public String detalhes(@RequestParam String protocolo, Model model) {

        Solicitacao solicitacao = service.buscarPorProtocolo(protocolo);

        if (solicitacao == null) {
            return "redirect:/gestor/solicitacoes";
        }

        carregarDadosSolicitacao(solicitacao, model);

        return "gestor/detalhes";
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam String usuario, @RequestParam String senha) {

        if ("gestor@email.com".equals(usuario) && "123".equals(senha)) {
            return "redirect:/gestor/painel";
        }

        return "redirect:/gestor/login";
    }

    @PostMapping("/atualizar-status")
    public String atualizarStatus(
            @RequestParam String protocolo, @RequestParam String novoStatus,
            @RequestParam String comentario, RedirectAttributes redirectAttributes,
            Model model) {

        try {
            Status status = Status.valueOf(novoStatus);

            service.atualizarStatus(protocolo, status, "Gestor",
                    comentario, null);

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Status atualizado para " +
                            status.getDescricao());

            return "redirect:/gestor/detalhes?protocolo=" + protocolo;

        } catch (IllegalArgumentException e) {

            Solicitacao solicitacao = service.buscarPorProtocolo(protocolo);

            carregarDadosSolicitacao(solicitacao, model);

            model.addAttribute("erro", e.getMessage());

            return "gestor/atualizar";
        }
    }

    private void carregarDadosSolicitacao(Solicitacao solicitacao, Model model) {

        model.addAttribute("solicitacao", solicitacao);
        model.addAttribute("progresso", solicitacao.getStatus().getProgresso());

        Status proximoStatus = solicitacao.getStatus().proximo();

        model.addAttribute("proximoStatus", proximoStatus);

        if (proximoStatus != null) {
            model.addAttribute("proximoStatusDescricao",
                    proximoStatus.getDescricao());
        }
    }

}
