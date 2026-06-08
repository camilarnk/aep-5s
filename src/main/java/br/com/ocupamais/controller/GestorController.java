package br.com.ocupamais.controller;

import br.com.ocupamais.model.Solicitacao;
import br.com.ocupamais.service.SolicitacaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                int progresso = switch (solicitacao.getStatus()) {
                    case ABERTO -> 20;
                    case TRIAGEM -> 40;
                    case EM_EXECUCAO -> 60;
                    case RESOLVIDO -> 80;
                    case ENCERRADO -> 100;
                };

                model.addAttribute("solicitacao", solicitacao);
                model.addAttribute("progresso", progresso);

                model.addAttribute("proximoStatus",
                        solicitacao.getStatus().proximo());

                model.addAttribute("proximoStatusDescricao",
                        solicitacao.getStatus().proximo().getDescricao());

            } else {
                model.addAttribute("erro",
                        "Nenhuma solicitação encontrada para o protocolo informado.");
            }
        }

        return "gestor/atualizar";
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam String usuario, @RequestParam String senha) {

        if ("gestor@email.com".equals(usuario) && "123".equals(senha)) {
            return "redirect:/gestor/painel";
        }

        return "redirect:/gestor/login";
    }

}
