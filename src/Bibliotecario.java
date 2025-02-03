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

    public void menu() {
        Bibliotecario usuarioVez = this;

        Scanner entrada = new Scanner(System.in);
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("Informe qual ação deseja fazer:");
        System.out.println("1- Cadastrar Usuário");//feito
        System.out.println("2- Registrar devolução");//aletrar Status no arquivo histórico
        System.out.println("3- Desbolquar usuário");//feito
        System.out.println("4- Lista de obras emprestadas");//feito
        System.out.println("5- Lista de usuários com atraso nos empréstimos");//feito
        System.out.println("6- Consultar informações da obra");// feito
        System.out.println("7- Sair"); // feito
        int escolha = entrada.nextInt();
        entrada.nextLine();

        switch (escolha) {
            case 1:
                boolean validaResposta = false;

                while (validaResposta == false) {
                    System.out.println("Para cadastrar Professor, digite 1.");
                    System.out.println("Para cadastrar Aluno, digite 2.");
                    System.out.println("Para cadastrar Bibliotecário, digite 3.");

                    int resposta = entrada.nextInt();
                    entrada.nextLine();
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
                System.out.println("Informe o nome do livro que está sendo devolvido: ");
                String nomeLivro = entrada.nextLine();
                chamarBiblioteca(email, nomeLivro);
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
                while (respostaValida == false) {

                    System.out.println("Para consultar um livro através do Id, digite 1.");
                    System.out.println("Para consultar um livro através do título, digite 2.");
                    int resposta = entrada.nextInt();
                    entrada.nextLine();
                    if (resposta == 1) {
                        System.out.println("Informe o Id do livro que deseja consultar: ");
                        String id = entrada.nextLine();
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
        this.menu();
    }

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
        salvarDados();
        Main.main(new String[] {});

    }

    @Override
    public void salvarDados() {
        super.salvarDados();
        Biblioteca.atualizarDadosLogin(this);
    }

    public void chamarBiblioteca(String email, String nomeLivro) {
        
        Biblioteca.registrarDevolucao(email, nomeLivro);
        Biblioteca.atualizaStatus(email, nomeLivro);
        Biblioteca.gerarUsuariosBloqueados();
        acrescentarDevolucao();

    }

    public void desbloquearUsuario() {
        Biblioteca.gerarUsuariosBloqueados();
    }

    public void listarObrasEmpres() {

        System.out.println("Relatório de histórico gerado!");
    }

    public void listarUsuariosAtrasos() {
        Biblioteca.gerarUsuariosBloqueados();

        System.out.println("Relatório gerado com sucesso!");

    }
}
