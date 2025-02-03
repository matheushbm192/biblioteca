import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

//declaração de variáveis 
//classe filha de usuário 
public class Bibliotecario extends Usuario {
    String telefone;
    int qntDevolucoes = 0;
    
    //construtor
    public Bibliotecario(String nome, String email, String senha, String telefone) {
        super(nome, email, senha);
        this.telefone = telefone;
    }

    //métodos get 
    public String getTelefone() {
        return telefone;
    }

    public int getQntDevolucoes() {
        return qntDevolucoes;
    }

    //método que adiciona a quantidade de devoluções que o bibliotecário fez
    public void acrescentarDevolucao() {
        this.qntDevolucoes += 1;
    }

    //método que exibe menu de bibliotecário
    public void menu() {
        //atribui a variável o usuário da vez 
        Bibliotecario usuarioVez = this;

        Scanner entrada = new Scanner(System.in);
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("Informe qual ação deseja fazer:");
        System.out.println("1- Cadastrar Usuário");
        System.out.println("2- Registrar devolução");
        System.out.println("3- Desbolquar usuário");
        System.out.println("4- Lista de obras emprestadas");
        System.out.println("5- Lista de usuários com atraso nos empréstimos");
        System.out.println("6- Consultar informações da obra");
        System.out.println("7- Sair");
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
                            //chama método passando tipo 1 -> Professor
                            cadastrarUsuario(1);
                            validaResposta = true;
                            break;
                        case 2:
                            //chama método passando tipo 2 -> Aluno
                            cadastrarUsuario(2);
                            validaResposta = true;
                            break;
                        case 3:
                             //chama método passando tipo 3 -> Bibliotecário
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
                //armazena email do usuário
                String email = entrada.nextLine();
                System.out.println("Informe o nome do livro que está sendo devolvido: ");
                //armazena nome do Livro a ser devolvido
                String nomeLivro = entrada.nextLine();
                //chama método psaando email e nome do livro como parâmetros
                chamarBiblioteca(email, nomeLivro);
                break;
            case 3:
                //chama método
                desbloquearUsuario();
                break;
            case 4:
                //chama método
                listarObrasEmpres();
                break;
            case 5:
                //chama método
                listarUsuariosAtrasos();
                break;
            case 6:

                boolean respostaValida = false;
                //roda enquanto resposta do usuário não for válida
                while (!respostaValida) {

                    System.out.println("Para consultar um livro através do Id, digite 1.");
                    System.out.println("Para consultar um livro através do título, digite 2.");
                    //armazena resposta do usuário
                    int resposta = entrada.nextInt();
                    entrada.nextLine();
                    if (resposta == 1) {
                        System.out.println("Informe o Id do livro que deseja consultar: ");
                        String id = entrada.nextLine();
                        //obra -> variável que armazena informações da obra retornada
                        Obra obra = Biblioteca.consultarObraId(id);
                        //método que imprimi informações da obra
                        Biblioteca.imprimirResultadoConsulta(obra);
                        //valida resposta do usuário
                        respostaValida = true;
                    } else if (resposta == 2) {
                        System.out.println("Informe o título do livro que deseja consultar: ");
                        String titulo = entrada.nextLine();
                        //obra -> variável que armazena informações da obra retornada
                        Obra obra = Biblioteca.consultarObraTitulo(titulo);
                        //método que imprimi informações da obra
                        Biblioteca.imprimirResultadoConsulta(obra);
                        //valida resposta do usuário
                        respostaValida = true;
                    } else {
                        respostaValida = false;
                    }

                    break;
                }

                break;
            case 7:
            //chama método
                sair();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente!");
                
        }
        //chama menu após usuário terminar sua ação 
        this.menu();
    }

    //método que guarda informações de cadastro do usuário 
    // tipo -> informa qual o tipo de usuário (Professor, Aluno, Bibliotecário)
    public void cadastrarUsuario(int tipo) {
        Scanner entrada = new Scanner(System.in);
        //guarda todas es informações
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
            //instância um novo Professor 
            Professor novoProfessor = new Professor(nome, email, senha, departamento);
            //chama método que adiciona cadastro no arquivo de login
            Biblioteca.addLoginProfessor(novoProfessor);

        } else if (tipo == 2) {
            // Aluno
            System.out.println("Informe a matrícula: ");
            String matricula = entrada.nextLine();
            System.out.println("Informe o curso ao qual está vinculado: ");
            String curso = entrada.nextLine();
            //instância um novo Aluno 
            Aluno novoAluno = new Aluno(nome, email, senha, matricula, curso);
            //chama método que adiciona cadastro no arquivo de login
            Biblioteca.addLoginAluno(novoAluno);

        } else {
            //Bibliotecário
            System.out.println("Informe o número de telefone: ");
            String telefone = entrada.nextLine();
            //instância um novo Bibliotecário 
            Bibliotecario novoBibliotecario = new Bibliotecario(nome, email, senha, telefone);
            //chama método que adiciona cadastro no arquivo de login
            Biblioteca.addLoginBibliotecario(novoBibliotecario);
        }

    }

    //chama método main para reiniciar o programa 
    public void sair() {
        //chama função que salva dados do bibliotecário
        salvarDados();
        Main.main(new String[] {});

    }

    //método pertencente a classe mãe
    @Override
    public void salvarDados() {
        super.salvarDados();
        //chama método que atualiza os dados do 
        //bibliotecário passando ele mesmo como parâmetro
        Biblioteca.atualizarDadosLogin(this);
    }

    //chama métodos pertencentes a biblioteca para realizar uma devolução 
    public void chamarBiblioteca(String email, String nomeLivro) {
        //registra a devolução 
        Biblioteca.registrarDevolucao(email, nomeLivro);
        //atualiza status do empréstimo (entregue ou emprestado)
        Biblioteca.atualizaStatus(email, nomeLivro);
        //atualiza o relatório de usuários bloqueados 
        Biblioteca.gerarUsuariosBloqueados();
        //acresenta mais uma devolução no histórico do bibliotecário 
        acrescentarDevolucao();
        Biblioteca.atualizarAcervo(nomeLivro);

    }

    public void desbloquearUsuario() {
        //atualiza relatório de usuários bloqueados 
        Biblioteca.gerarUsuariosBloqueados();
    }

    public void listarObrasEmpres() {
        // relatório presente no arquivo txt 
        System.out.println("Relatório de histórico gerado!");
    }

    public void listarUsuariosAtrasos() {
        //atualiza usuários bloqueados 
        Biblioteca.gerarUsuariosBloqueados();
         
        //relatório disponível em arquivo txt 
        System.out.println("Relatório gerado com sucesso!");

    }
}
