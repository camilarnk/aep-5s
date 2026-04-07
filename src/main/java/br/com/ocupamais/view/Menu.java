package br.com.ocupamais.view;

import br.com.ocupamais.controller.SolicitacaoController;
import br.com.ocupamais.model.Categoria;
import br.com.ocupamais.model.Prioridade;
import br.com.ocupamais.model.Solicitacao;
import br.com.ocupamais.model.Status;

import java.util.List;
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
        int opcao;

        do {
            System.out.println("\n----------------------");
            System.out.println("    MENU DO CIDADÃO   ");
            System.out.println("----------------------");

            System.out.println("O que deseja realizar?");
            System.out.println("1- Criar Solicitação");
            System.out.println("2- Buscar por Protocolo");
            System.out.println("3- Voltar");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao) {
                case 1 -> criarSolicitacao();
                case 2 -> buscarPorProtocolo();
                case 3 -> System.out.println("Voltando ao Menu Inicial...");
                default -> System.out.println("Opção Inválida!");
            }
        } while(opcao != 3);
    }

    private void menuGestor() {
        int opcao;

        do {
            System.out.println("\n---------------------");
            System.out.println("    MENU DO GESTOR   ");
            System.out.println("---------------------");

            System.out.println("O que deseja realizar?");
            System.out.println("1- Listar Solicitações da População");
            System.out.println("2- Filtrar Solicitações da População");
            System.out.println("3- Atualizar Status de uma Demanda");
            System.out.println("4- Voltar");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao) {
                case 1 -> listarSolicitacoes();
                case 2 -> filtrarSolicitacoes();
                case 3 -> atualizarStatus();
                case 4 -> System.out.println("Voltando ao Menu Inicial...");
                default -> System.out.println("Opção Inválida!");
            }
        } while(opcao != 4);
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
            System.out.println("\nSelecione a Categoria:");
            Categoria.exibirOpcoes();

            try {
                categoria = lerCategoria();
            } catch (IllegalArgumentException e) {
                System.out.println("Categoria inválida.");
            }
        }

        Prioridade prioridade = null;

        while(prioridade == null) {
            System.out.println("\nSelecione a Prioridade:");
            Prioridade.exibirOpcoes();

            try {
                prioridade = lerPrioridade();
            } catch (IllegalArgumentException e) {
                System.out.println("\nNível de prioridade inválido.");
            }
        }

        try {
            Solicitacao s = controller.criarSolicitacao(descricao, localizacao, categoria, prioridade, anonimo, nome);
            System.out.println("Sua solicitação foi criada com sucesso!");
            System.out.println("Protocolo: " + s.getProtocolo());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void buscarPorProtocolo() {
        System.out.println("Digite o protocolo: ");
        String protocolo = scanner.nextLine();

        Solicitacao solicitacao = controller.buscarPorProtocolo(protocolo);

        if(solicitacao == null) {
            System.out.println("Solicitação não encontrada.");
            return;
        } else {
            System.out.println(solicitacao.resumoSolicitacao());
        }

        System.out.println("Deseja ver também o histórico de atualizações?\n Digite S/N");
        String opcao = scanner.nextLine();

        if(opcao.equalsIgnoreCase("S")) {
            System.out.println(solicitacao.historicoSolicitacao());
        }
    }

    private void listarSolicitacoes() {
        System.out.println("Como deseja listar?");
        System.out.println("1- Ver todas (incluindo encerradas)");
        System.out.println("2- Ver apenas não encerradas");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch(opcao) {
            case 1 -> controller.listarSolicitacoes().forEach(System.out::println);
            case 2 -> controller.listarSolicitacoesAbertas().forEach(System.out::println);
            default -> System.out.println("Opção Inválida.");
        }
    }

    private void filtrarSolicitacoes() {
        System.out.println("Selecione o tipo de filtro: ");
        System.out.println("1- Por Categoria");
        System.out.println("2- Por Prioridade");

        int tipoFiltro = scanner.nextInt();
        scanner.nextLine();

        switch(tipoFiltro) {
            case 1 -> {
                Categoria categoria = null;

                while(categoria == null) {
                    System.out.println("\nSelecione a Categoria:");
                    Categoria.exibirOpcoes();

                    try {
                        categoria = lerCategoria();
                    } catch (IllegalArgumentException e) {
                        System.out.println("Categoria inválida.");
                    }
                }

                List<Solicitacao> resultado = controller.filtrarPorCategoria(categoria);
                if(resultado.isEmpty()) {
                    System.out.println("Nenhuma solicitação com o filtro " + categoria + " encontrada!");
                } else {
                    resultado.forEach(System.out::println);
                }
            }

            case 2 -> {
                Prioridade prioridade = null;

                while(prioridade == null) {
                    System.out.println("Selecione a prioridade:");
                    Prioridade.exibirOpcoes();

                    try {
                        prioridade = lerPrioridade();
                    } catch (IllegalArgumentException e) {
                        System.out.println("Prioridade inválida.");
                    }
                }

                List<Solicitacao> resultado = controller.filtrarPorPrioridade(prioridade);
                if(resultado.isEmpty()) {
                    System.out.println("Nenhuma solicitação com o filtro " + prioridade + " encontrada!");
                } else {
                    resultado.forEach(System.out::println);
                }
            }

            default -> System.out.println("Opção Inválida!");
        }
    }

    private void atualizarStatus() {
        System.out.println("Digite o protocolo: ");
        String protocolo = scanner.nextLine();

        Solicitacao solicitacao = controller.buscarPorProtocolo(protocolo);

        if(solicitacao == null) {
            System.out.println("Solicitação não encontrada.");
            return;
        }

        Status atual = solicitacao.getStatus();
        Status proximo = null;

        if(atual == Status.ENCERRADO) {
            System.out.println("A solicitação já foi encerrada.");
            return;
        } else {
            proximo = switch(atual) {
                case ABERTO -> Status.TRIAGEM;
                case TRIAGEM -> Status.EM_EXECUCAO;
                case EM_EXECUCAO -> Status.RESOLVIDO;
                case RESOLVIDO -> Status.ENCERRADO;
                default -> atual;
            };
        }

        System.out.println("Status atual: " + atual);
        System.out.println("Deseja avançar para: " + proximo + "? (S/N)");
        String opcao = scanner.nextLine();

        if(opcao.equalsIgnoreCase("N")) {
            System.out.println("Operação cancelada.");
            return;
        }

        Status status = proximo;

        System.out.println("Responsável pela mudança:");
        String responsavel = scanner.nextLine();

        System.out.println("Comentário:");
        String comentario = scanner.nextLine();

        try {
            controller.atualizarStatus(protocolo, status, responsavel, comentario);
            System.out.println("Status alterado de " + atual + " para " + status + " com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private Categoria lerCategoria() {
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return Categoria.buscarPeloId(opcao);
    }

    private Prioridade lerPrioridade() {
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return Prioridade.buscarPeloId(opcao);
    }
}
