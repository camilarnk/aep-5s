package br.com.ocupamais.controller;
import br.com.ocupamais.service.SolicitacaoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}