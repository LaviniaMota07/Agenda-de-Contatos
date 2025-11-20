package app;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import exception.ContatoNaoEncontradoException;
import model.Contato;
import model.TipoContato;         
import persistence.PersistenciaAgenda;
import service.Agenda;

public class AgendaApp {

    private static final String ARQUIVO_DADOS = "agenda-contatos.txt";

    private static Agenda agenda = new Agenda();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            PersistenciaAgenda.carregar(agenda, ARQUIVO_DADOS);
            System.out.println("Dados carregados com sucesso.");
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível carregar os dados da agenda.");
            System.out.println("Detalhes: " + e.getMessage());
        }

        int opcao;

        do {
            mostrarMenu();
            opcao = lerInteiro("Escolha uma opçao: ");

            try {
                switch (opcao) {
                    case 1:
                        cadastrarContato();
                        break;
                    case 2:
                        listarContatos();
                        break;
                    case 3:
                        buscarPorNome();
                        break;
                    case 4:
                        editarContato();
                        break;
                    case 5:
                        removerContato();
                        break;
                    case 0:
                        System.out.println("Saindo da agenda. Até mais!");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro de validação: " + e.getMessage());
            } catch (ContatoNaoEncontradoException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Erro ao acessar o arquivo de dados: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado.");
                System.out.println("Detalhes técnicos (para debug): " + e.getMessage());
            }

            System.out.println();

        } while (opcao != 0);
    }

    private static void mostrarMenu() {
        System.out.println("==== AGENDA DE CONTATOS ====");
        System.out.println("1 - Cadastrar contato");
        System.out.println("2 - Listar contatos");
        System.out.println("3 - Buscar contato por nome");
        System.out.println("4 - Editar contato");
        System.out.println("5 - Remover contato");
        System.out.println("0 - Sair");
        System.out.println("============================");
    }

    private static void cadastrarContato() throws IOException {
        System.out.println("== Novo Contato ==");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        TipoContato tipo = escolherTipoContato();

        Contato c = agenda.adicionarContato(nome, telefone, email, tipo);
        System.out.println("Contato cadastrado com sucesso! ID: " + c.getId());
        PersistenciaAgenda.salvar(agenda, ARQUIVO_DADOS);
    }

    private static void listarContatos() {
        System.out.println("== Lista de Contatos ==");
        List<Contato> contatos = agenda.listarContatos();

        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato cadastrado.");
            return;
        }

        for (Contato c : contatos) {
            System.out.println(c);
        }
    }

    private static void buscarPorNome() {
        System.out.print("Digite o nome (ou parte do nome) para buscar: ");
        String nome = scanner.nextLine();

        List<Contato> encontrados = agenda.buscarPorNome(nome);

        if (encontrados.isEmpty()) {
            System.out.println("Nenhum contato encontrado.");
        } else {
            System.out.println("Contatos encontrados:");
            for (Contato c : encontrados) {
                System.out.println(c);
            }
        }
    }

    private static void editarContato() throws ContatoNaoEncontradoException, IOException {
        int id = lerInteiro("Digite o ID do contato que deseja editar: ");

        Contato contato = agenda.buscarPorId(id);
        System.out.println("Editando: " + contato);

        System.out.print("Novo nome (enter para manter): ");
        String nome = scanner.nextLine();
        if (nome.isBlank()) {
            nome = null;
        }

        System.out.print("Novo telefone (enter para manter): ");
        String telefone = scanner.nextLine();
        if (telefone.isBlank()) {
            telefone = null;
        }

        System.out.print("Novo email (enter para manter): ");
        String email = scanner.nextLine();
        if (email.isBlank()) {
            email = null;
        }

        System.out.print("Alterar tipo? (s/n): ");
        String resp = scanner.nextLine();
        TipoContato novoTipo = null;
        if (resp.equalsIgnoreCase("s")) {
            novoTipo = escolherTipoContato();
        }

        agenda.atualizarContato(id, nome, telefone, email, novoTipo);
        System.out.println("Contato atualizado com sucesso.");

        PersistenciaAgenda.salvar(agenda, ARQUIVO_DADOS);
    }

    private static void removerContato() throws ContatoNaoEncontradoException, IOException {
        int id = lerInteiro("Digite o ID do contato que deseja remover: ");

        agenda.removerContato(id);
        System.out.println("Contato removido com sucesso.");

        PersistenciaAgenda.salvar(agenda, ARQUIVO_DADOS);
    }

    private static TipoContato escolherTipoContato() {
        System.out.println("Tipo de contato:");
        System.out.println("1 - Pessoal");
        System.out.println("2 - Trabalho");
        System.out.println("3 - Familia");
        System.out.println("4 - Outros");

        int opcao = lerInteiro("Escolha o tipo: ");

        switch (opcao) {
            case 1:
                return TipoContato.PESSOAL;
            case 2:
                return TipoContato.TRABALHO;
            case 3:
                return TipoContato.FAMILIA;
            default:
                return TipoContato.OUTROS;
        }
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                String linha = scanner.nextLine();
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }
}
