package br.com.ocupamais.view;

import br.com.ocupamais.controller.SolicitacaoController;

import java.util.Scanner;

public class Menu {

    private final SolicitacaoController controller;
    private final Scanner scanner;

    public Menu(SolicitacaoController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {

    }
}
