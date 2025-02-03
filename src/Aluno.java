import java.util.Scanner;

//declaração de variváveis 
//classe filha de usuário 
public class Aluno extends Usuario {
    private String matricula;
    private String curso;
    private final int limiteEmprestimo = 2;

    // construtor
    public Aluno(String nome, String email, String senha, String matricula, String curso) {
        super(nome, email, senha);
        this.matricula = matricula;
        this.curso = curso;

    }

    // construtor
    public Aluno(String nome, String email, String senha, String matricula) {
        super(nome, email, senha);
        this.matricula = matricula;

    }

    // métodos de get
    public String getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return curso;
    }

    public int getLimiteEmprestimo() {
        return limiteEmprestimo;
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    // método que exibe menu
    public void menu() {
        Scanner entrada = new Scanner(System.in);
        System.out.println();
        System.out.println("----------------------------------------------------");
        System.out.println("Informe qual ação deseja fazer:");
        System.out.println("1- Realizar Empréstimo");
        System.out.println("2- Consultar Informações da obra");
        System.out.println("3 - Sair");

        // guarda escolha do usuário
        int escolha = entrada.nextInt();
        // quebra de linha
        entrada.nextLine();
        System.out.println();

        switch (escolha) {
            case 1:
                // (this) -> passa usuário da vez
                Biblioteca.realizarEmprestimo(this);
                break;
            case 2:
                while (true) {

                    System.out.println("Para consultar um livro através do Id, digite 1.");
                    System.out.println("Para consultar um livro através do título, digite 2.");
                    int resposta = entrada.nextInt();
                    entrada.nextLine();

                    if (resposta == 1) {
                        System.out.println("Informes o Id do livro que deseja consultar: ");
                        String id = entrada.nextLine();
                        // obra -> armazena obra retornada pela função
                        Obra obra = Biblioteca.consultarObraId(id);
                        // imprimi informações da obra
                        Biblioteca.imprimirResultadoConsulta(obra);

                        break;
                    } else if (resposta == 2) {
                        System.out.println("Informe o título do livro que deseja consultar: ");
                        String titulo = entrada.nextLine();
                        entrada.nextLine();
                        Obra obra = Biblioteca.consultarObraTitulo(titulo);
                        Biblioteca.imprimirResultadoConsulta(obra);
                        break;
                    }
                }
                break;
            case 3:
                // chama função
                sair();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente!");
        }
        // chama menu novamente ao final da ação
        this.menu();
    }

    // chama método main para reiniciar o programa
    public static void sair() {
        Main.main(new String[] {});
    }

}
