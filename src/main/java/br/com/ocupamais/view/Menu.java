package br.com.ocupamais.view;

import br.com.ocupamais.controller.SolicitacaoController;
import br.com.ocupamais.model.Categoria;
import br.com.ocupamais.model.Prioridade;
import br.com.ocupamais.model.Solicitacao;
import br.com.ocupamais.model.Status;

import java.time.LocalDateTime;
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
            System.out.println("\n========================================");
            System.out.println("         BEM-VINDO AO OCUPAMAIS         ");
            System.out.println("========================================");

            System.out.println("Como qual tipo de usuário deseja entrar?");
            System.out.println("\n1- Cidadão");
            System.out.println("2- Gestor");
            System.out.println("0- Sair\n");

            perfil = lerInteiro();

            switch(perfil) {
                case 1 -> menuCidadao();
                case 2 -> menuGestor();
                case 0 -> System.out.println("==== OBRIGADO POR UTILIZAR O OCUPAMAIS ====");
                default -> System.out.println("\nOpção Inválida!\n");
            }
        } while(perfil != 0);
    }

    private void menuCidadao() {
        int opcao;

        do {
            System.out.println("\n======================");
            System.out.println("    MENU DO CIDADÃO   ");
            System.out.println("======================");

            System.out.println("\n1- Criar Solicitação");
            System.out.println("2- Buscar por Protocolo");
            System.out.println("3- Voltar\n");

            opcao = lerInteiro();

            switch(opcao) {
                case 1 -> {
                    criarSolicitacao();
                    continuar();
                }
                case 2 -> {
                    buscarPorProtocolo();
                    continuar();
                }
                case 3 -> System.out.println("Voltando ao Menu Inicial...");
                default -> System.out.println("\nOpção Inválida!\n");
            }
        } while(opcao != 3);
    }

    private void menuGestor() {
        int opcao;

        do {
            System.out.println("\n=====================");
            System.out.println("    MENU DO GESTOR   ");
            System.out.println("=====================");

            System.out.println("\n1- Listar Solicitações da População");
            System.out.println("2- Filtrar Solicitações da População");
            System.out.println("3- Atualizar Status de uma Demanda");
            System.out.println("4- Voltar\n");

            opcao = lerInteiro();

            switch(opcao) {
                case 1 -> {
                    listarSolicitacoes();
                    continuar();
                }
                case 2 -> {
                    filtrarSolicitacoes();
                    continuar();
                }
                case 3 -> {
                    atualizarStatus();
                    continuar();
                }
                case 4 -> System.out.println("Voltando ao Menu Inicial...");
                default -> System.out.println("\nOpção Inválida!\n");
            }
        } while(opcao != 4);
    }

    private void criarSolicitacao() {
        int anonimoTemp;

        do {
            System.out.println("\n===========================");
            System.out.println("  CRIANDO UMA SOLICITAÇÃO  ");
            System.out.println("===========================");

            System.out.println("Deseja publicar identificado ou anonimamente?\n");
            System.out.println("0- Publicar Identificado");
            System.out.println("1- Publicar Anonimamente");

            System.out.print("\nDigite o número correspondente a opção: ");
            anonimoTemp = lerInteiro();

        } while (anonimoTemp != 0 && anonimoTemp != 1);

        boolean anonimo = anonimoTemp != 0;

        String nome = "";
        if(!anonimo) {
            while(nome.isBlank()) {
                System.out.print("\nDigite seu nome: ");
                nome = scanner.nextLine();
            }
        }

        System.out.print("\nDescreva o problema: ");
        String descricao = scanner.nextLine();

        System.out.print("\nLocalização do problema: ");
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
            System.out.println("\nSua solicitação foi criada com sucesso!");
            System.out.println("Protocolo: " + s.getProtocolo());
        } catch (Exception e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    private void buscarPorProtocolo() {
        System.out.print("\nDigite o protocolo: ");
        String protocolo = scanner.nextLine();

        Solicitacao solicitacao = controller.buscarPorProtocolo(protocolo);

        if(solicitacao == null) {
            System.out.println("Solicitação não encontrada.");
            return;
        } else {
            System.out.println(solicitacao.resumoSolicitacao());
            System.out.println("========================================");
        }

        String opcao;

        do {
            System.out.println("Deseja ver também o histórico de atualizações? (S/N)");
            opcao = scanner.nextLine();

            if (!opcao.equalsIgnoreCase("S") && !opcao.equalsIgnoreCase("N")) {
                System.out.println("Digite apenas S ou N.");
            }

        } while (!opcao.equalsIgnoreCase("S") && !opcao.equalsIgnoreCase("N"));

        if(opcao.equalsIgnoreCase("S")) {
            System.out.println(solicitacao.historicoSolicitacao());
            System.out.print("========================================");
        }
    }

    private void listarSolicitacoes() {
        int opcao;

        do {
            System.out.println("\nComo deseja listar?");
            System.out.println("\n1- Todas (incluindo encerradas)");
            System.out.println("2- Apenas abertas\n");

            opcao = lerInteiro();

            System.out.println("\n================================================\n");

            switch(opcao) {
                case 1 -> controller.listarSolicitacoes().forEach(System.out::println);
                case 2 -> controller.listarSolicitacoesAbertas().forEach(System.out::println);
                default -> System.out.println("\nOpção Inválida.\n");
            }
        } while(opcao != 1 && opcao != 2);

        System.out.println("\n================================================");
    }

    private void filtrarSolicitacoes() {
        int tipoFiltro;

        while(true) {
            System.out.println("\nSelecione o tipo de filtro: ");
            System.out.println("\n1- Por Categoria");
            System.out.println("2- Por Prioridade");
            System.out.println("3- Por Localização\n");

            tipoFiltro = lerInteiro();

            if (tipoFiltro >= 1 && tipoFiltro <= 3) {
                break;
            }
            System.out.println("\nOpção inválida!\n");
        }

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
                    System.out.println("\nNenhuma solicitação com a categoria " + categoria + " encontrada!");
                } else {
                    System.out.println("\n================================================\n");
                    resultado.forEach(System.out::println);
                    System.out.println("\n================================================");
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
                    System.out.println("\nNenhuma solicitação com a prioridade " + prioridade + " encontrada!");
                } else {
                    System.out.println("\n================================================\n");
                    resultado.forEach(System.out::println);
                    System.out.println("\n================================================");
                }
            }

            case 3 -> {
                System.out.print("Digite a localização desejada: ");
                String localizacao = scanner.nextLine();

                List<Solicitacao> resultado = controller.filtrarPorLocalizacao(localizacao);
                if(resultado.isEmpty()) {
                    System.out.println("\nNenhuma solicitação no local " + localizacao + " encontrada!");
                } else {
                    System.out.println("\n================================================\n");
                    resultado.forEach(System.out::println);
                    System.out.println("\n================================================");
                }
            }

            default -> System.out.println("\nOpção Inválida!\n");
        }
    }

    private void atualizarStatus() {
        System.out.print("\nDigite o protocolo: ");
        String protocolo = scanner.nextLine();

        Solicitacao solicitacao = controller.buscarPorProtocolo(protocolo);

        if(solicitacao == null) {
            System.out.println("\nSolicitação não encontrada.");
            return;
        }

        Status atual = solicitacao.getStatus();
        Status proximo;

        if(atual == Status.ENCERRADO) {
            System.out.println("\nA solicitação já foi encerrada.");
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

        String justificativa = null;

        if(LocalDateTime.now().isAfter(solicitacao.getPrazo())) {
            System.out.println("\nAtenção! Esta solicitação está atrasada!");

            do {
                System.out.println("\nInforme a justificativa do atraso:");
                justificativa = scanner.nextLine();

                if(justificativa.isBlank()) {
                    System.out.println("\nJustificativa é obrigatória!");
                }

            } while(justificativa.isBlank());
        }

        System.out.println("\nStatus atual: " + atual);
        String opcao;

        do {
            System.out.print("Deseja avançar para: " + proximo + "? (S/N): ");
            opcao = scanner.nextLine();

            if (!opcao.equalsIgnoreCase("S") && !opcao.equalsIgnoreCase("N")) {
                System.out.println("Entrada inválida. Digite apenas S ou N.");
            }

        } while (!opcao.equalsIgnoreCase("N") && !opcao.equalsIgnoreCase("S"));

        if(opcao.equalsIgnoreCase("N")) {
            System.out.println("Operação cancelada.");
            return;
        }

        Status status = proximo;

        System.out.print("\nResponsável pela mudança: ");
        String responsavel = scanner.nextLine();

        System.out.print("\nComentário: ");
        String comentario = scanner.nextLine();

        try {
            controller.atualizarStatus(protocolo, status, responsavel, comentario, justificativa);
            System.out.println("Solicitacao " + protocolo +
                    " teve status alterado de " + atual + " para " + status + " com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private int lerInteiro() {
        while(true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Entrada inválida. Digite um número: ");
            }
        }
    }

    private Categoria lerCategoria() {
        int opcao = lerInteiro();
        return Categoria.buscarPeloId(opcao);
    }

    private Prioridade lerPrioridade() {
        int opcao = lerInteiro();
        return Prioridade.buscarPeloId(opcao);
    }

    private void continuar() {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

}
