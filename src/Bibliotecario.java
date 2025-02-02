import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Bibliotecario extends Usuario {
    String telefone;
    int qntDevolucoes = 0;
    

    public Bibliotecario(String nome, String email, String senha, String telefone) {
        super(nome, email, senha);
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public int getQntDevolucoes() {
        return qntDevolucoes;
    }

    public void acrescentarDevolucao() {
        this.qntDevolucoes += 1;
    }

    public void menu(Object usuario) {

        int quantDevolucoes = 0;

        if (usuario instanceof Bibliotecario) {
            Bibliotecario usuarioVez = (Bibliotecario) usuario; 
            quantDevolucoes = usuarioVez.getQntDevolucoes();
        }

        Scanner entrada = new Scanner(System.in);

        System.out.println("Informe qual ação deseja fazer:");
        System.out.println("1- Cadastrar Usuário");// adicionar no arquivo
        System.out.println("2- Registrar devolução");//aletrar Status no arquivo histórico
        System.out.println("3- Desbolquar usuário");//feito
        System.out.println("4- Lista de obras emprestadas");//feito
        System.out.println("5- Lista de usuários com atraso nos empréstimos");// feito
        System.out.println("6- Consultar informações da obra");// feito
        System.out.println("7- Sair"); // feito
        int escolha = entrada.nextInt();

        switch (escolha) {
            case 1:
                boolean validaResposta = false;

                while (validaResposta = false) {
                    System.out.println("Para cadastrar Professor, digite 1.");
                    System.out.println("Para cadastrar Aluno, digite 2.");
                    System.out.println("Para cadastrar Bibliotecário, digite 3.");

                    int resposta = entrada.nextInt();
                    switch (resposta) {
                        case 1:
                            cadastrarUsuario(1);
                            validaResposta = true;
                            break;
                        case 2:
                            cadastrarUsuario(2);
                            validaResposta = true;
                            break;
                        case 3:
                            cadastrarUsuario(3);
                            validaResposta = true;
                            break;

                        default:
                            System.out.println("Reposta inválida. Tente novamente.");
                            validaResposta = false;
                            break;
                    }
                }

                break;
            case 2:
                System.out.println("Informe o email do usuário que está realizando a devolução: ");
                String email = entrada.nextLine();
                registrarDevolucao(email);
                break;
            case 3:
                desbloquearUsuario();
                break;
            case 4:
                listarObrasEmpres();
                break;
            case 5:
                listarUsuariosAtrasos();
                break;
            case 6:

                boolean respostaValida = false;
                while (respostaValida = false) {

                    System.out.println("Para consultar um livro através do Id, digite 1.");
                    System.out.println("Para consultar um livro através do título, digite 2.");
                    int resposta = entrada.nextInt();

                    if (resposta == 1) {
                        System.out.println("Informe o Id do livro que deseja consultar: ");
                        int id = entrada.nextInt();
                        Obra obra = Biblioteca.consultarObraId(id);
                        Biblioteca.imprimirResultadoConsulta(obra);
                        respostaValida = true;
                    } else if (resposta == 2) {
                        System.out.println("Informe o título do livro que deseja consultar: ");
                        String titulo = entrada.nextLine();
                        Obra obra = Biblioteca.consultarObraTitulo(titulo);
                        Biblioteca.imprimirResultadoConsulta(obra);

                        respostaValida = true;
                    } else {
                        respostaValida = false;
                    }

                    break;
                }

                break;
            case 7:
                sair();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente!");
                
        }
    }

    // registra usuarios
    public void cadastrarUsuario(int tipo) {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Informe o nome do usuário: ");
        String nome = entrada.nextLine();
        System.out.println("Informe o email: ");
        String email = entrada.nextLine();
        System.out.println("Usuário, digite a senha: ");
        String senha = entrada.nextLine();

        if (tipo == 1) {
            // Professor
            System.out.println("Informe o departamento ao qual está vinculado: ");
            String departamento = entrada.nextLine();
            Professor novoProfessor = new Professor(nome, email, senha, departamento);
            Biblioteca.addLoginProfessor(novoProfessor);

        } else if (tipo == 2) {
            // Aluno
            System.out.println("Informe a matrícula: ");
            String matricula = entrada.nextLine();
            System.out.println("Informe o curso ao qual está vinculado: ");
            String curso = entrada.nextLine();
            Aluno novoAluno = new Aluno(nome, email, senha, matricula, curso);
            Biblioteca.addLoginAluno(novoAluno);

        } else {
            System.out.println("Informe o número de telefone: ");
            String telefone = entrada.nextLine();
            Bibliotecario novoBibliotecario = new Bibliotecario(nome, email, senha, telefone);
            Biblioteca.addLoginBibliotecario(novoBibliotecario);
        }

    }

    public void sair() {

        // salvar todos os registros feitos no dia
       
    }

    public void registrarDevolucao(String email) {
        // implementar
        // Biblioteca.atualizarInformacoes();
        ArrayList<String[]> linhas = Biblioteca.lerDados();

        for (String[] linha : linhas) {
            if (linha[0].equals(email)) {

            }
        }
        acrescentarDevolucao();

        Biblioteca.gerarUsuariosBloqueados();

    }

    public void desbloquearUsuario() {
        Biblioteca.gerarUsuariosBloqueados();
    }

    public void listarObrasEmpres() {
        ArrayList<Emprestimos> historico = Biblioteca.lerHistoricoEmprestimos();

        for (Emprestimos emprestimo : historico) {
            System.out.println("Id: " + emprestimo.getId());
            System.out.println("Título: " + emprestimo.getTitulo());
            System.out.println("Data: " + emprestimo.getData());
            System.out.println("Email do usuário: " + emprestimo.getEmail());
            System.out.println("Status: " + emprestimo.getStatus());
            System.out.println("------------------------------");
        }
    }

    public void listarUsuariosAtrasos() {
        Biblioteca.gerarUsuariosBloqueados();
        ArrayList<String[]> linhas = Biblioteca.lerUsuariosBloqueados();

        for (String[] linha : linhas) {
            System.out.println("Usuário bloqueado:");
            System.out.println(" - Email: " + linha[0]);
            System.out.println(" - Nome do livro: " + linha[1]);
            System.out.println(" - Data de bloqueio: " + linha[2]);
            System.out.println("------------------------------");
        }

    }
}
