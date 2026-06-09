package br.com.ocupamais.controller;
import br.com.ocupamais.model.Categoria;
import br.com.ocupamais.model.Prioridade;
import br.com.ocupamais.model.Solicitacao;
import br.com.ocupamais.service.SolicitacaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    private final SolicitacaoService service;

    public SolicitacaoController(SolicitacaoService service) {
        this.service = service;
    }

    @GetMapping("/nova")
    public String novaSolicitacao() {
        return "solicitacoes/nova-solicitacao";
    }

    @GetMapping("/acompanhar")
    public String acompanharSolicitacao() {
        return "solicitacoes/acompanhar";
    }

    @PostMapping
    public String salvarSolicitacao(
            @RequestParam String descricao,
            @RequestParam String endereco,
            @RequestParam String bairro,
            @RequestParam(required = false) String referencia,
            @RequestParam Categoria categoria,
            @RequestParam Prioridade prioridade,
            @RequestParam boolean anonimo,
            @RequestParam(required = false) String nomeSolicitante,
            Model model
    ) {

        Solicitacao solicitacao = service.criarSolicitacao(
                descricao, endereco, bairro, referencia, categoria,
                prioridade, anonimo, nomeSolicitante
        );

        model.addAttribute("protocolo", solicitacao.getProtocolo());

        return "solicitacoes/sucesso";
    }

    @PostMapping("/acompanhar")
    public String buscarSolicitacao(@RequestParam String protocolo, Model model) {
        Solicitacao solicitacao = service.buscarPorProtocolo(protocolo);

        if (solicitacao == null) {
            model.addAttribute("erro",
                    "Nenhuma solicitação encontrada para esse protocolo.");
            return "solicitacoes/acompanhar";
        }

        model.addAttribute("solicitacao", solicitacao);

        return "solicitacoes/acompanhar";
    }

}