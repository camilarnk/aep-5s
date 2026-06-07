package br.com.ocupamais.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/gestor")
public class GestorController {

    @GetMapping("/login")
    public String login() {
        return "gestor/login";
    }

    @GetMapping("/painel")
    public String painel() {
        return "gestor/painel";
    }

    @GetMapping("/solicitacoes")
    public String solicitacoes() {
        return "gestor/solicitacoes";
    }

    @GetMapping("/atualizar")
    public String atualizar() {
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
