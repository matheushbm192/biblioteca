import java.util.Scanner;
import java.util.UUID;

public class Bibliotecario extends Usuario {
    String telefone;
    int qntDevolucoes;
    

    public Bibliotecario(String nome, String email, String senha, String telefone) {
        super(nome, email, senha);
        this.telefone = telefone;
    }

    public static void menu() {

        Scanner entrada = new Scanner(System.in);

        System.out.println("Informe qual ação deseja fazer:");
        System.out.println("1- Cadastrar Usuário");
        System.out.println("2- Registrar devolução");
        System.out.println("3- Desbolquar usuário");
        System.out.println("4- Lista de obras emprestadas");
        System.out.println("5- Lista de usuários com atraso nos empréstimos");
        System.out.println("6- Consultar informações da obra");
        System.out.println("7- Sair");
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
                registrarDevolucao();
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
                Biblioteca.consultarObras();
                break;
            case 7: 
            sair();
            break;
            default:
                System.out.println("Opção inválida. Tente novamente!");
                menu();
        }
    }

    // registra usuarios
    public static void cadastrarUsuario(int tipo) {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Informe o nome do usuário: ");
        String nome = entrada.nextLine();
        System.out.println("Informe o email: ");
        String email = entrada.nextLine();
        System.out.println("Usuário, digite a senha: ");
        String senha = entrada.nextLine(); 

        if(tipo == 1){
            //Professor 
            System.out.println("Informe o departamento ao qual está vinculado: ");
            String departamento = entrada.nextLine();
            Professor novoProfessor = new Professor(nome, email, senha, departamento);

        }else if(tipo == 2){
            //Aluno
            System.out.println("Informe a matrícula: ");
            String matricula = entrada.nextLine();
            System.out.println("Informe o curso ao qual está vinculado: ");
            String curso = entrada.nextLine();
            Aluno novoAluno = new Aluno(nome, email, senha, matricula, curso);


        }else{
            System.out.println("Informe o número de telefone: ");
            String telefone = entrada.nextLine();
            Bibliotecario novoBibliotecario = new Bibliotecario(nome, email, senha, telefone);
        }

        //chamar função para adicionar usuário no arquivo 


    }

    public static void sair(){

        //salvar todos os registros feitos no dia 
        Biblioteca.atualizarInformacoes();
        TelaInicio.inicio();
    }
    
    public static void registrarDevolucao(){
        //implementar
        Biblioteca.atualizarInformacoes();
    }

    public static void desbloquearUsuario(){

    }

    public static void listarObrasEmpres(){

    }

    public static void listarUsuariosAtrasos(){

    }

}
