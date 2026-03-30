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
        int perfil;

        do {
            System.out.println("---- BEM-VINDO AO OCUPAMAIS ----");
            System.out.println("Como qual tipo de usuário deseja entrar?");
            System.out.println("1- Cidadão");
            System.out.println("2- Gestor");
            System.out.println("0- Sair");

            perfil = scanner.nextInt();
            scanner.nextLine();

            switch(perfil) {
                case 1 -> menuCidadao();
                case 2 -> menuGestor();
                case 0 -> System.out.println("Encerrando o Sistema...");
                default -> System.out.println("Opção Inválida!");
            }
        } while(perfil != 0);
    }

    public void menuCidadao() {
        System.out.println("O que deseja realizar?");
        System.out.println("1- Criar Solicitação");
        System.out.println("2- Buscar por Protocolo");

        int op = scanner.nextInt();
        scanner.nextLine();
    }

    public void menuGestor() {

    }
}
