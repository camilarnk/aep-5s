package br.com.ocupamais.view;

import br.com.ocupamais.controller.SolicitacaoController;
import br.com.ocupamais.model.Categoria;
import br.com.ocupamais.model.Prioridade;

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
                case 0 -> System.out.println("---- OBRIGADO POR UTILIZAR O OCUPAMAIS ----");
                default -> System.out.println("Opção Inválida!");
            }
        } while(perfil != 0);
    }

    private void menuCidadao() {
        System.out.println("O que deseja realizar?");
        System.out.println("1- Criar Solicitação");
        System.out.println("2- Buscar por Protocolo");
        System.out.println("3- Voltar");

        int op = scanner.nextInt();
        scanner.nextLine();

        switch(op) {
            case 1 -> criarSolicitacao();
            case 2 -> buscarSolicitacao();
            case 3 -> { return; }
            default -> System.out.println("Opção Inválida!");
        }
    }

    private void menuGestor() {
        System.out.println("O que deseja realizar?");
        System.out.println("1- Listar Solicitações da População");
        System.out.println("2- Atualizar Status de uma Demanda");
        System.out.println("3- Voltar");

        int op = scanner.nextInt();
        scanner.nextLine();

        switch(op) {
            case 1 -> controller.listarSolicitacoes().forEach(System.out::println);
            case 2 -> atualizarStatus();
            case 3 -> { return; }
            default -> System.out.println("Opção Inválida!");
        }
    }

    private void criarSolicitacao() {
        System.out.println("\n--- CRIANDO UMA SOLICITAÇÃO ----\n");

        System.out.println("Deseja publicar identificado ou anonimamente?\n");
        System.out.println("0- Publicar Identificado");
        System.out.println("1- Publicar Anonimamente");
        System.out.println("\nDigite o número correspondente a opção: ");

        int anonimoTemp = scanner.nextInt();
        scanner.nextLine();

        boolean anonimo = anonimoTemp != 0;

        String nome = "";
        if(!anonimo) {
            while(nome.isBlank()) {
                System.out.println("Digite seu nome: ");
                nome = scanner.nextLine();
            }
        }

        System.out.println("\nDescreva o problema: ");
        String descricao = scanner.nextLine();

        System.out.println("\nLocalização do problema: ");
        String localizacao = scanner.nextLine();

        Categoria categoria = null;

        while(categoria == null) {
            System.out.println("\nCategoria (ILUMINAÇÃO, BURACO, LIMPEZA, SAÚDE, SEGURANÇA, EDUCAÇÃO, OUTROS): ");

            try {
                categoria = Categoria.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("\nCategoria inválida. Tente novamente.");
            }
        }

        Prioridade prioridade = null;

        while(prioridade == null) {
            System.out.println("\nPrioridade (BAIXA, MEDIA, ALTA): ");

            try {
                prioridade = Prioridade.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("\nNível de prioridade inválido. Tente novamente.");
            }
        }

        try {
            controller.criarSolicitacao(descricao, localizacao, categoria, prioridade, anonimo, nome);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }


    }

    private void buscarSolicitacao() {

    }

    private void atualizarStatus() {

    }

}
